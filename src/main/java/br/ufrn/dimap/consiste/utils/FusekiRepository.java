package br.ufrn.dimap.consiste.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;

import br.ufrn.dimap.consiste.filter.FilterService;
import br.ufrn.dimap.consiste.pubsub.PublisherService;
import br.ufrn.dimap.consiste.topic.TopicEntity;
import br.ufrn.dimap.consiste.topic.TopicService;

@Repository
public abstract class FusekiRepository {
	
	private static final Logger LOGGER = Logger.getLogger(FusekiRepository.class); 

	@Autowired
	private TopicService topicService;
	
	@Autowired
	private PublisherService publisherService;
	
	protected List<String> getBasicUnits(){
		List<String> units = Arrays.asList("http://www.w3.org/2001/XMLSchema#string", "http://www.w3.org/2001/XMLSchema#boolean", "http://www.w3.org/2001/XMLSchema#decimal", "http://www.w3.org/2001/XMLSchema#float", "http://www.w3.org/2001/XMLSchema#double",
							"http://www.w3.org/2001/XMLSchema#dateTime", "http://www.w3.org/2001/XMLSchema#time", "http://www.w3.org/2001/XMLSchema#date", "http://www.w3.org/2001/XMLSchema#gYearMonth", "http://www.w3.org/2001/XMLSchema#gYear", "http://www.w3.org/2001/XMLSchema#gMonthDay",
							"http://www.w3.org/2001/XMLSchema#gDay,http://www.w3.org/2001/XMLSchema#gMonth", "http://www.w3.org/2001/XMLSchema#hexBinary", "http://www.w3.org/2001/XMLSchema#base64Binary", "http://www.w3.org/2001/XMLSchema#anyURI",
							"http://www.w3.org/2001/XMLSchema#normalizedString", "http://www.w3.org/2001/XMLSchema#token", "http://www.w3.org/2001/XMLSchema#language", "http://www.w3.org/2001/XMLSchema#NMTOKEN", "http://www.w3.org/2001/XMLSchema#Name",
							"http://www.w3.org/2001/XMLSchema#NCName", "http://www.w3.org/2001/XMLSchema#integer", "http://www.w3.org/2001/XMLSchema#nonPositiveInteger", "http://www.w3.org/2001/XMLSchema#negativeInteger",
							"http://www.w3.org/2001/XMLSchema#long", "http://www.w3.org/2001/XMLSchema#int", "http://www.w3.org/2001/XMLSchema#short", "http://www.w3.org/2001/XMLSchema#byte", "http://www.w3.org/2001/XMLSchema#nonNegativeInteger",
							"http://www.w3.org/2001/XMLSchema#unsignedLong", "http://www.w3.org/2001/XMLSchema#unsignedInt", "http://www.w3.org/2001/XMLSchema#unsignedShort", "http://www.w3.org/2001/XMLSchema#unsignedByte",
							"http://www.w3.org/2001/XMLSchema#positiveInteger");
		return units;
	}
	
	@Async
	public Future<ResultSet> search(String url, String query) {
		try {
			QueryExecution qe = QueryExecutionFactory.sparqlService(url+"/query", query);
			ResultSet resultSet = qe.execSelect();
			return new AsyncResult<ResultSet>(resultSet);
		} catch (Exception e) {
			return new AsyncResult<ResultSet>(null);
		}
	}
	
	public void updateRequest(String url, String query, String domainName) {
		List<TopicEntity> topics = topicService.getTopicsByDomain(domainName);
		
		// Verify if some of the topic queries are satisfied by the new record		
		if(topics.size() > 0) {
			OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
			UpdateAction.parseExecute(query, om);
			
			for (int i = 0; i < topics.size() ; i++) {
				TopicEntity topic = topics.get(i);
				try {
					Query jenaQuery = QueryFactory.create(topics.get(i).getQuery());
					QueryExecution qe = QueryExecutionFactory.create(jenaQuery, om);
					
					ResultSet rs = qe.execSelect();
					
					if (rs.hasNext()) {
						String message = ResultSetFormatter.asXMLString(rs);
						if (topic.hasFilter()) {
							DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
							Document document = parser.parse(new InputSource(new StringReader(message)));
				            NodeList list = document.getElementsByTagName("binding");
				            if (!FilterService.executeFilter(topic, list)) {
				            	continue;
				            }
				            for (int j = 0; j < list.getLength(); j++) {
				        		Node n = list.item(j);
				        		String attribut = n.getAttributes().getNamedItem("name").getNodeValue();
				        		if (attribut.equals("date")){
				        			topicService.updateLastDate(Double.parseDouble(n.getChildNodes().item(1).getFirstChild().getNodeValue()), topic.getTopic());
				        		}
				        		if (attribut.equals("value")){
				        			topicService.updateLastValue(Double.parseDouble(n.getChildNodes().item(1).getFirstChild().getNodeValue()), topic.getTopic());
				        		}
				        	}
				            
						}
						LOGGER.info(message);
						publisherService.sendMessage(topic, message);
					}					
				} catch (Exception e) {
				}				
			}
		}
		
		UpdateRequest updateRequest = UpdateFactory.create(query);
		
		UpdateProcessor proc = UpdateExecutionFactory.createRemote(updateRequest, url + "/update");
		proc.execute();
				
	}
	
	protected List<String> getOne(String query, String url, String variableName){
		List<String> resultList = new ArrayList<String>();
		try {
			QueryExecution qe = QueryExecutionFactory.sparqlService(url+"/query", query);
			ResultSet resultSet = qe.execSelect();
			while(resultSet.hasNext()){
				QuerySolution qs = resultSet.next();
				resultList.add(qs.get(variableName).toString());
			}
			qe.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	public List<String> getObservedProperties(String url) {
		StringBuilder sb = new StringBuilder();
		sb.append(" PREFIX uomvocab: <http://purl.oclc.org/NET/muo/muo#>");
		sb.append(" SELECT ?property WHERE {");
		sb.append(" ?property a uomvocab:PhysicalQuality . }");
		return this.getOne(sb.toString(), url, "property");
	}
	
	@Async
	public Future<List<String>> getAllCriterionURIs(String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX qoc: <http://consiste.dimap.ufrn.br/ontologies/qodisco/context#>");
		sb.append(" SELECT ?criterion WHERE { ");
		sb.append(" ?criterion a qoc:QoC . }");
		
		return new AsyncResult<List<String>>(getOne(sb.toString(), url, "criterion"));
	}
	
	public List<String> getUnits(String ontologyUrl){
		StringBuilder sb = new StringBuilder();
		sb.append(" PREFIX uomvocab: <http://purl.oclc.org/NET/muo/muo#>");
		sb.append(" SELECT ?unit WHERE {");
		sb.append(" ?unit a uomvocab:UnitOfMeasurement . }");
		
		List<String> units = new ArrayList<String>(getBasicUnits());
		units.addAll(getOne(sb.toString(), ontologyUrl, "unit"));
		
		return units;
	}
	
}
