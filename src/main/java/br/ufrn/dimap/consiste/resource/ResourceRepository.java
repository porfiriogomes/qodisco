package br.ufrn.dimap.consiste.resource;

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
public class ResourceRepository extends FusekiRepository {
	
	protected StringBuilder getPrefixes() {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");
		sb.append(" PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>");
		sb.append(" PREFIX uomvocab: <http://purl.oclc.org/NET/muo/muo#>");
		sb.append(" PREFIX qodisco: <http://consiste.dimap.ufrn.br/ontologies/qodisco#>");
		sb.append(" PREFIX dul: <http://www.loa-cnr.it/ontologies/DUL.owl#>");

		return sb;
	}
	
	public List<String> getDeviceTypes(String url) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?type WHERE {");
		sb.append(" ?type rdfs:subClassOf ssn:Device . }");
		
		return this.getOne(sb.toString(), url, "type");
	}
	
	public void addResource(String url,
			ResourceEntity resource, String domainName) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" INSERT DATA {");
		sb.append(String.format(" qodisco:%s a <%s> ;", resource.getName(), resource.getType()));
				
		if(resource.getLocation()!=null && !resource.getLocation().equals("None"))
			sb.append(String.format(" dul:hasLocation <%s> ;", resource.getLocation()));
		
		if (resource.getProperties() != null){
			for(int i = 0 ; i < resource.getProperties().size() ; i++)
				sb.append(String.format(" ssn:observes <%s> ;", resource.getProperties().get(i)));
		}
		
		if(resource.getDescription()!=null && !resource.getDescription().trim().equals(""))
			sb.append(String.format(" rdfs:comment '%s' . }", resource.getDescription()));
		
		System.out.println(sb.toString());

		this.updateRequest(url, sb.toString(), domainName);
	}
	
	@Async
	public Future<List<ResourceEntity>> search(ResourceSearchEntity resourceSearch,
			String url) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?type ?property ?value WHERE {");
		
		if(!resourceSearch.getSearchFor().equals("None"))
			sb.append(String.format(" ?type a <%s> .", resourceSearch.getSearchFor()));
		
		if(!resourceSearch.getLocation().equals("None"))
			sb.append(String.format(" ?type dul:hasLocation <%s> .", resourceSearch.getLocation()));
		
		if(resourceSearch.getProperties()!=null){
			for(String property : resourceSearch.getProperties())
				sb.append(String.format(" ?type ssn:observes <%s> .", property));
		}
		sb.append(" ?type ?property ?value . }");
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(url+"/query", sb.toString());
		ResultSet resultSet = qe.execSelect();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> properties = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		while(resultSet.hasNext()){
			QuerySolution qs = resultSet.next();
			names.add(qs.get("type").asNode().getLocalName());
			properties.add(qs.get("property").asNode().getLocalName());
			if(qs.get("value").isLiteral()){
				values.add(qs.get("value").asLiteral().getString());
			} else{
				values.add(qs.get("value").asNode().getLocalName());
			}
		}
		
		qe.close();
		
		return new AsyncResult<List<ResourceEntity>>(transformArrayInResourceEntities(names, properties, values));
	}
	
	protected List<ResourceEntity> transformArrayInResourceEntities(List<String> names, List<String> properties, List<String> values){
		List<ResourceEntity> resources = new ArrayList<ResourceEntity>();
		
		for(int a = 0; a < names.size() ; a++){
			String name = names.get(a);

			int index = containsResource(resources, name);
			if(index==-1){
				ResourceEntity resource = new ResourceEntity();
				resource.setName(name);
				if(properties.get(a).equals("type"))
					resource.setType(values.get(a));
				if(properties.get(a).equals("hasLocation"))
					resource.setLocation(values.get(a));
				if(properties.get(a).equals("observes")){
					List<String> resourceProperties = new ArrayList<String>();
					resourceProperties.add(values.get(a));
					resource.setProperties(resourceProperties);
				}
				resources.add(resource);
			} else{
				if(properties.get(a).equals("type"))
					resources.get(index).setType(values.get(a));
				if(properties.get(a).equals("hasLocation"))
					resources.get(index).setLocation(values.get(a));
				if(properties.get(a).equals("observes")){
					List<String> resourceProperties = null;
					if(resources.get(index).getProperties()==null){						
						resourceProperties = new ArrayList<String>();
					}else{
						resourceProperties = resources.get(index).getProperties();
					}
					resourceProperties.add(values.get(a));
					resources.get(index).setProperties(resourceProperties);
				}
			}
		}
		return resources;
	}
	
	protected int containsResource(List<ResourceEntity> list, String name){
		for(int a = 0; a < list.size() ; a++){
			if(list.get(a).getName().equals(name))
				return a;
		}
		return -1;
	}
	
}
