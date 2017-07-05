package br.ufrn.dimap.consiste.service;

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
public class ServiceRepository extends FusekiRepository {
	
	protected StringBuilder getPrefixes(){
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");
		sb.append(" PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>");
		sb.append(" PREFIX qodisco: <http://consiste.dimap.ufrn.br/ontologies/qodisco#>");
		sb.append(" PREFIX service: <http://www.daml.org/services/owl-s/1.1/Service.owl#>");
		sb.append(" PREFIX profile: <http://www.daml.org/services/owl-s/1.1/Profile.owl#>");
		sb.append(" PREFIX process: <http://www.daml.org/services/owl-s/1.1/Process.owl#>");		
		
		return sb;
	}
	
	@Async
	public Future<List<String>> getResources(String url) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?resource WHERE {");
		sb.append(" ?parent rdfs:subClassOf ssn:Device .");
		sb.append(" ?resource a ?parent . }");
		
		return new AsyncResult<List<String>>(this.getOne(sb.toString(), url, "resource"));
	}
	
	public void addService(String url, ServiceEntity service, String domainName) {
		StringBuilder sb = this.getPrefixes();
		
		sb.append(" INSERT DATA {");
		sb.append(String.format(" qodisco:%s a service:Service ;", service.getName()));
		
		if(!service.getResource().equals("None"))
			sb.append(String.format(" qodisco:exposed_by <%s> ;", service.getResource()));
		
		sb.append(String.format(" service:presents qodisco:%sProfile ;", service.getName()));
		sb.append(String.format(" service:describedBy qodisco:%sProcess .", service.getName()));
		
		// Service Profile		
		sb.append(String.format(" qodisco:%sProfile a service:Profile ;", service.getName()));
		sb.append(String.format(" service:presentedBy qodisco:%s ;", service.getName()));
		if(service.getDescription()!=null && service.getDescription().trim()!="")
			sb.append(String.format(" profile:textDescription '%s' .", service.getDescription()));
		
		// Service Process		
		sb.append(String.format(" qodisco:%sProcess a process:AtomicProcess .", service.getName()));
		if(service.getInputs() != null){
			for(IOEntity i : service.getInputs()){
				sb.append(String.format(" qodisco:%sProcess process:hasInput qodisco:%s .", service.getName(), i.getName()));
				sb.append(String.format(" qodisco:%s a process:Input ;", i.getName()));
				sb.append(String.format(" process:parameterType <%s> .", i.getType()));
			}			
		}
		if(service.getOutputs() != null){
			for(IOEntity o : service.getOutputs()){
				sb.append(String.format(" qodisco:%sProcess process:hasOutput qodisco:%s .", service.getName(), o.getName()));
				sb.append(String.format(" qodisco:%s a process:Output ;", o.getName()));
				sb.append(String.format(" process:parameterType <%s> .", o.getType()));
			}			
		}
		sb.append(String.format(" qodisco:%sProcess service:describes qodisco:%s . }", service.getName(), service.getName()));
		
		this.updateRequest(url, sb.toString(), domainName);
	}
	
	// Can be optimized to include other service attributes such as the service inputs and outputs
	@Async
	public Future<List<ServiceEntity>> getServices(String url,
			ServiceSearchEntity serviceSearch) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?service ?description WHERE {");
		sb.append(" ?service a service:Service ;");
		if(!serviceSearch.getResource().equals("None"))
			sb.append(String.format(" qodisco:exposed_by <%s> ;", serviceSearch.getResource()));
		
		sb.append(" service:describedBy ?process ;");
		sb.append(" service:presents ?profile .");
		sb.append(" ?profile profile:textDescription ?description . }");
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(url+"/query", sb.toString());
		
		ResultSet resultSet = qe.execSelect();
		
		List<ServiceEntity> services = new ArrayList<ServiceEntity>();
				
		while(resultSet.hasNext()){
			QuerySolution qs = resultSet.next();
			ServiceEntity service = new ServiceEntity();
			service.setName(qs.get("service").asNode().getLocalName());
			service.setDescription(qs.get("description").toString());
		
			services.add(service);
		}
		
		qe.close();
		
		return new AsyncResult<List<ServiceEntity>>(services);
	}
	
}
