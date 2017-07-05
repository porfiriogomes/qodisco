package br.ufrn.dimap.consiste.location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import br.ufrn.dimap.consiste.utils.FusekiRepository;

@Repository
public class LocationRepository extends FusekiRepository{

	protected StringBuilder getPrefixes(){
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");
		sb.append(" PREFIX geos: <http://pervasive.semanticweb.org/ont/2004/06/space#>");
		sb.append(" PREFIX geom: <http://pervasive.semanticweb.org/ont/2004/06/geo-measurement#>");
		sb.append(" PREFIX qodisco: <http://consiste.dimap.ufrn.br/ontologies/qodisco#>");

		return sb;
	}
	
	public List<String> getFixedStructures(String url) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?fixedStructure WHERE {");
		sb.append(" ?fixedStructure rdfs:subClassOf geos:FixedStructure . }");
		
		return this.getOne(sb.toString(), url, "fixedStructure");		
	}
	
	@Async
	public Future<List<String>> getLocations(String url){
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?location WHERE {");
		sb.append(" ?parent rdfs:subClassOf geos:FixedStructure .");
		sb.append(" ?location a ?parent . }");
		
		return new AsyncResult<List<String>>(this.getOne(sb.toString(), url, "location"));
	}
	
	@Async
	public Future<List<LocationEntity>> getLocationsFull(String url){
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?name ?parent ?type ?latitude ?longitude ?altitude WHERE {");
		sb.append(" ?type rdfs:subClassOf geos:FixedStructure . ");
		sb.append(" ?location a ?type ; geos:name ?name . ");
		sb.append(" OPTIONAL { ?location geos:spatiallySubsumes ?parent ; } ");
		sb.append(" OPTIONAL { ?location geos:hasCoordinates ?coordinates . ?coordinates a geom:LocationCoordinates ; geom:altitude ?altitude ; geom:latitude ?latitude ; geom:longitude ?longitude . } } ");
		
		List<LocationEntity> locations = new ArrayList<LocationEntity>();
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(url+"/query", sb.toString());
		
		ResultSet resultSet = qe.execSelect();
		
		while(resultSet.hasNext()){
			QuerySolution qs = resultSet.next();
			LocationEntity location = new LocationEntity();
			
			location.setName(qs.get("name").toString());
			location.setType(qs.get("type").asNode().getLocalName());
			
			if (qs.get("parent")!=null)
				location.setParent(qs.get("parent").asNode().getLocalName());
			
			if (qs.get("latitude")!=null)
				location.setLatitude(qs.get("latitude").asLiteral().getDouble());
			
			if (qs.get("longitude")!=null)
				location.setLongitude(qs.get("longitude").asLiteral().getDouble());
			
			if (qs.get("altitude")!=null)
				location.setAltitude(qs.get("altitude").asLiteral().getDouble());			

			locations.add(location);
		}
		
		qe.close();
	
		return new AsyncResult<List<LocationEntity>>(locations);
	}

	public void addLocation(LocationEntity location, String url, String domainName) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" INSERT DATA {");
		sb.append(String.format(" qodisco:%s a <%s> ;", location.getName(), location.getType()));
		
		if(location.getParent() != null && !location.getParent().equals("None"))
			sb.append(String.format(" geos:spatiallySubsumes <%s> ;", location.getParent()));
		if(location.getLatitude() != null && location.getLongitude() != null){
			sb.append(String.format(" geos:hasCoordinates qodisco:coordinates_%s ;", location.getName()));
			insertCoordinates(location.getName(), location.getLatitude(), location.getLongitude(), location.getAltitude(), url, domainName);
		}
		sb.append(String.format(" geos:name '%s' . }", location.getName()));	
		
		this.updateRequest(url, sb.toString(), domainName);
	}

	private void insertCoordinates(String name, Double latitude,
			Double longitude, Double altitude, String url, String domainName) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" INSERT DATA {");
		sb.append(String.format(" qodisco:coordinates_%s a geom:LocationCoordinates ;", name));
		
		if(altitude!=null)
			sb.append(String.format(" geom:altitude %s ;", altitude.toString()));
		
		sb.append(String.format(" geom:latitude %s ;", latitude.toString()));
		sb.append(String.format(" geom:longitude %s . }", longitude.toString()));
		
		this.updateRequest(url, sb.toString(), domainName);
	}
	
}
