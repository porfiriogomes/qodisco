package br.ufrn.dimap.consiste.qoc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import br.ufrn.dimap.consiste.utils.FusekiRepository;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Repository
public class QoCRepository extends FusekiRepository {

	protected StringBuilder getPrefixes(){
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX qodisco: <http://consiste.dimap.ufrn.br/ontologies/qodisco#>");
		sb.append(" PREFIX qoc: <http://consiste.dimap.ufrn.br/ontologies/qodisco/context#>");
		sb.append(" PREFIX uomvocab: <http://purl.oclc.org/NET/muo/muo#>");
		
		return sb;
	}
	
	@Async
	public Future<List<QoCCriterionEntity>> getAllCriteriaFullDescription(String url) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?criterion ?property ?obj WHERE { ");
		sb.append(" ?criterion a qoc:QoC . ");
		sb.append(" ?criterion ?property ?obj . }");
		
		try {
			QueryExecution qe = QueryExecutionFactory.sparqlService(url+"/query", sb.toString());
			ResultSet resultSet = qe.execSelect();
			List<String> criteria = new ArrayList<String>();
			List<String> properties = new ArrayList<String>();
			List<String> objects = new ArrayList<String>();
			
			while(resultSet.hasNext()){
				QuerySolution qs = resultSet.next();
				criteria.add(qs.get("criterion").toString());
				properties.add(qs.get("property").toString());
				if(qs.get("obj").isLiteral()){
					objects.add(qs.get("obj").asLiteral().getValue().toString());
				} else{
					objects.add(qs.get("obj").toString());
				}
			}
			qe.close();
			return new AsyncResult<List<QoCCriterionEntity>>(convertListsToQoC(criteria, properties, objects, url));
		} catch (Exception e) {
			return new AsyncResult<List<QoCCriterionEntity>>(new ArrayList<QoCCriterionEntity>());
		}
	}
	
	protected List<QoCCriterionEntity> convertListsToQoC(List<String> criteria, List<String> properties, List<String> objects, String repositoryUrl){
		QoCCriterionEntity criterion = new QoCCriterionEntity();
		List<QoCCriterionEntity> result = new ArrayList<QoCCriterionEntity>();
		
		if(criteria!=null && criteria.size()>0){
			String cname = criteria.get(0);
			for(int a = 0; a < criteria.size() ; a++){
				if(criteria.get(a).equals(cname)){
					switch (properties.get(a)) {
						case "http://consiste.dimap.ufrn.br/ontologies/qodisco/context#name":
							criterion.setName(objects.get(a));
							break;
						case "http://consiste.dimap.ufrn.br/ontologies/qodisco/context#description":
							criterion.setDescription(objects.get(a));
							break;
						case "http://consiste.dimap.ufrn.br/ontologies/qodisco/context#invariant":
							criterion.setInvariant(Boolean.valueOf(objects.get(a)));
							break;
						case "http://consiste.dimap.ufrn.br/ontologies/qodisco/context#direction":
							criterion.setDirection(objects.get(a));
							break;
						case "http://consiste.dimap.ufrn.br/ontologies/qodisco/context#maximum_value":
							criterion.setMaximumValue(Integer.valueOf(objects.get(a)));
							break;
						case "http://consiste.dimap.ufrn.br/ontologies/qodisco/context#minimum_value":
							criterion.setMinimumValue(Integer.valueOf(objects.get(a)));
							break;
						case "http://consiste.dimap.ufrn.br/ontologies/qodisco/context#unit":
							criterion.setUnit(objects.get(a));
							break;
						case "http://consiste.dimap.ufrn.br/ontologies/qodisco/context#composed_by":
							List<String> composedByList = criterion.getComposedBy();
							if(composedByList == null)
								composedByList = new ArrayList<String>();
							composedByList.add(objects.get(a));
							criterion.setComposedBy(composedByList);
							break;
						default:
							break;
					}
				} else {
					criterion.setRepositoryUrl(repositoryUrl);
					result.add(criterion);
					criterion = new QoCCriterionEntity();
					cname = criteria.get(a);
					a--;
				}
			}
			criterion.setRepositoryUrl(repositoryUrl);
			result.add(criterion);
			return result;
		} else{
			return null;
		}
	}
	
	
	public void addQoCCriterion(String url, QoCCriterionEntity qoCCriterion, String domainName) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" INSERT DATA {");
		sb.append(String.format(" qodisco:%s a qoc:QoC ;", qoCCriterion.getName()));
		sb.append(String.format(" qoc:description '%s' ;", qoCCriterion.getDescription()));
		sb.append(String.format(" qoc:direction '%s' ;", qoCCriterion.getDirection()));
		sb.append(String.format(" qoc:invariant %b ;", qoCCriterion.getInvariant()));
		sb.append(String.format(" qoc:maximum_value %s ;", qoCCriterion.getMaximumValue().toString()));
		sb.append(String.format(" qoc:minimum_value %s ;", qoCCriterion.getMinimumValue().toString()));
	 	sb.append(String.format(" qoc:name '%s' ;", qoCCriterion.getName()));
	 	if(qoCCriterion.getComposedBy()!=null){
	 		for(String criterion : qoCCriterion.getComposedBy()){
	 			sb.append(String.format(" qoc:composed_by <%s> ;", criterion));
	 		}
	 	}
	 	sb.append(String.format(" qoc:unit <%s> . }", qoCCriterion.getUnit()));
	 	
		this.updateRequest(url, sb.toString(), domainName);
	}
	
	public QoCCriterionEntity getCriterion(String qoCName, String repositoryUrl) {
		StringBuilder sb = this.getPrefixes();
		sb.append(" SELECT ?criterion ?property ?obj WHERE {");
		sb.append(" ?criterion a qoc:QoC ;");
		sb.append(String.format(" qoc:name '%s' ;", qoCName));
		sb.append(" ?property ?obj . }");
		
		try {
			QueryExecution qe = QueryExecutionFactory.sparqlService(repositoryUrl+"/query", sb.toString());
			ResultSet resultSet = qe.execSelect();
			List<String> criteria = new ArrayList<String>();
			List<String> properties = new ArrayList<String>();
			List<String> objects = new ArrayList<String>();
			
			while(resultSet.hasNext()){
				QuerySolution qs = resultSet.next();
				criteria.add(qs.get("criterion").toString());
				properties.add(qs.get("property").toString());
				if(qs.get("obj").isLiteral()){
					objects.add(qs.get("obj").asLiteral().getValue().toString());
				} else{
					objects.add(qs.get("obj").toString());
				}
			}
			qe.close();
			
			return convertListsToQoC(criteria, properties, objects, repositoryUrl).get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
}
