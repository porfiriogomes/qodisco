package br.ufrn.dimap.consiste.qoc;

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
public class QoCService {

	@Autowired
	private DomainService domainService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private QoCRepository qoCRepository;
	
	public void create(String domainName, QoCCriterionEntity qoCCriterion){
		RepositoryEntity repo = repositoryService.getRandomAvailableRepository(domainName);
		if (repo != null)
			qoCRepository.addQoCCriterion(repo.getUrl(), qoCCriterion, domainName);
	}
	
	public List<QoCCriterionEntity> getAllCriteria(String domainName) {
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<QoCCriterionEntity> criteria = new ArrayList<QoCCriterionEntity>();
		List<Future<List<QoCCriterionEntity>>> futureResults = new ArrayList<Future<List<QoCCriterionEntity>>>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<QoCCriterionEntity>> futureResult = qoCRepository.getAllCriteriaFullDescription(repo.getUrl());
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
	
	public List<String> getAllCriterionURIs(String domainName){
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<String> criteria = new ArrayList<String>();
		List<Future<List<String>>> futureResults = new ArrayList<Future<List<String>>>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<String>> futureResult = qoCRepository.getAllCriterionURIs(repo.getUrl());
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
	
	public List<String> getDomainNames() {
		return domainService.getDomainNames();
	}
	
	public List<String> getUnits(String domainName) {
		RepositoryEntity repo = repositoryService.getRandomAvailableRepository(domainName);
		if (repo != null)
			return qoCRepository.getUnits(repo.getUrl());
		return new ArrayList<String>();
	}
	
	public QoCCriterionEntity getCriterion(String qoCName, String repositoryUrl) {
		return qoCRepository.getCriterion(qoCName, repositoryUrl);
	}
	
}
