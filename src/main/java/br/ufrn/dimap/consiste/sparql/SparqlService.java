package br.ufrn.dimap.consiste.sparql;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.sparql.util.ResultSetUtils;

import br.ufrn.dimap.consiste.domain.DomainService;
import br.ufrn.dimap.consiste.repository.RepositoryEntity;
import br.ufrn.dimap.consiste.repository.RepositoryService;
import br.ufrn.dimap.consiste.utils.FusekiRepository;

@Service
public class SparqlService {
	
	@Autowired
	private DomainService domainService;
	
	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private FusekiRepository sparqlRepository;

	public List<String> getDomainNames() {
		return domainService.getDomainNames();
	}

	public String search(String domainName, String query) {
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<ResultSet> results = new ArrayList<ResultSet>();
		List<Future<ResultSet>> futureResults = new ArrayList<Future<ResultSet>>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<ResultSet> futureResult = sparqlRepository.search(repo.getUrl(), query);

				futureResults.add(futureResult);
			}
			int i = 0;
			while(true){
				if(i == futureResults.size())
					break;
				if(!(futureResults.get(i).isDone())){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else{
					try {
						results.add(futureResults.get(i).get());
						i++;
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if (results.size() > 0) {			
			ResultSet result = ResultSetUtils.union(results.toArray(new ResultSet[results.size()]));		
			return ResultSetFormatter.asXMLString(result);
		}
		return "";
	}
	
}
