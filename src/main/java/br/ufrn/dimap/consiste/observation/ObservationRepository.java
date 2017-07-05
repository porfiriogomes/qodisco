package br.ufrn.dimap.consiste.observation;

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
public class ObservationRepository extends FusekiRepository {

	protected StringBuilder getPrefixes(){
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX qodisco: <http://consiste.dimap.ufrn.br/ontologies/qodisco#>");
		sb.append(" PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>");
		sb.append(" PREFIX qoc: <http://consiste.dimap.ufrn.br/ontologies/qodisco/context#>");
		sb.append(" PREFIX uomvocab: <http://purl.oclc.org/NET/muo/muo#>");
		
		return sb;
	}
	
	@Async
	public Future<List<ObservationEntity>> search(String url,
			ObservationSearchEntity observationSearch) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?observation ?observedProperty ?date ?observedBy ?criterion ?qocValue ?value WHERE {");
		sb.append(" ?observation a ssn:Observation ;");
		sb.append(" ssn:observationResultTime ?date ;");
		sb.append(" ssn:observedBy ?observedBy ;");
		sb.append(" ssn:observedProperty ?observedProperty ;");
		sb.append(" ssn:observationResult ?observationResult .");
		
		sb.append(" ?observationResult a ssn:SensorOutput ;");
		sb.append(" qodisco:has_qoc ?qocIndicator ;");
		sb.append(" ssn:hasValue ?observationValue .");
		
		sb.append(" ?qocIndicator a qodisco:QoCIndicator ;");
		sb.append(" qodisco:has_qoc_criterion ?criterion ;");
		sb.append(" qodisco:has_qoc_value ?qocValue .");
		
		sb.append(" ?observationValue a ssn:ObservationValue ;");
		sb.append(" ssn:hasQuantityValue ?value . ");
		
		if(!observationSearch.getQoCCriterion().equals("None") && observationSearch.getQoCValue()!=null)
			sb.append(String.format(" FILTER (?criterion = <%s> && ?qocValue %s %s)",
					observationSearch.getQoCCriterion(),
					observationSearch.getQoCComparison(),
					observationSearch.getQoCValue()));
		
		if(!observationSearch.getObservedProperty().equals("None"))
			sb.append(String.format(" FILTER (?observedProperty = <%s> )", observationSearch.getObservedProperty()));
		
		sb.append(" }");
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(url+"/query", sb.toString());
		
		ResultSet resultSet = qe.execSelect();
		List<ObservationEntity> observations = new ArrayList<ObservationEntity>();
		
		while(resultSet.hasNext()){
			QuerySolution qs = resultSet.next();
			ObservationEntity observation = new ObservationEntity();
			observation.setName(qs.get("observation").asNode().getLocalName());
			observation.setObservedProperty(qs.get("observedProperty").asNode().getLocalName());
			observation.setDate(qs.get("date").toString());
			observation.setObservedBy(qs.get("observedBy").asNode().getLocalName());
			observation.setCriterion(qs.get("criterion").asNode().getLocalName());
			String qocValue = qs.get("qocValue").toString();
			observation.setQoCValue(qocValue.substring(0, qocValue.indexOf("^")));
			observation.setValue(qs.get("value").asLiteral().getString());
		
			observations.add(observation);
		}
		
		qe.close();
		
		return new AsyncResult<List<ObservationEntity>>(observations);
	}
	
}
