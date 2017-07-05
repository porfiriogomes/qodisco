package br.ufrn.dimap.consiste.observation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.domain.DomainService;
import br.ufrn.dimap.consiste.repository.RepositoryEntity;
import br.ufrn.dimap.consiste.repository.RepositoryService;

@Service
public class ObservationService {

	@Autowired
	private DomainService domainService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ObservationRepository observationRepository;

	public List<String> getObservedProperties(String domainName) {
		RepositoryEntity repo = repositoryService.getRandomAvailableRepository(domainName);
		if (repo != null)
			return observationRepository.getObservedProperties(repo.getUrl());
		return new ArrayList<String>();
	}
	
	public List<String> getDomainNames() {
		return domainService.getDomainNames();
	}
	
	public List<String> getAllCriterionURIs(String domainName){
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<String> criteria = new ArrayList<String>();
		List<Future<List<String>>> futureResults = new ArrayList<Future<List<String>>>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<String>> futureResult = observationRepository.getAllCriterionURIs(repo.getUrl());
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
						criteria.addAll(futureResults.get(i).get());
						i++;
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return criteria;
	}
	
	public List<ObservationEntity> search(String domainName, ObservationSearchEntity observationSearch){
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<ObservationEntity> observations = new ArrayList<ObservationEntity>();
		List<Future<List<ObservationEntity>>> futureResults = new ArrayList<Future<List<ObservationEntity>>>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<ObservationEntity>> futureResult = observationRepository.search(repo.getUrl(), observationSearch);
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
						observations.addAll(futureResults.get(i).get());
						i++;
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		}
				
		return observations;
	}
	
}
