package br.ufrn.dimap.consiste.service;

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
public class ServiceService {

	@Autowired
	private DomainService domainService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	public List<String> getDomainNames() {
		return domainService.getDomainNames();
	}

	public List<String> getResourcesByDomain(String domainName) {
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<String> resources = new ArrayList<String>();
		List<Future<List<String>>> futureResults = new ArrayList<Future<List<String>>>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<String>> futureResult = serviceRepository.getResources(repo.getUrl());
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
						resources.addAll(futureResults.get(i).get());
						i++;
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		}
						
		return resources;
	}
	
	public void create(String domainName, ServiceEntity service) {
		RepositoryEntity repo = repositoryService.getRandomAvailableRepository(domainName);
		if (repo != null)
			serviceRepository.addService(repo.getUrl(), service, domainName);
	}
	
	public List<String> getUnits(String domainName) {
		RepositoryEntity repo = repositoryService.getRandomAvailableRepository(domainName);
		if (repo != null)
			return serviceRepository.getUnits(repo.getUrl());
		return new ArrayList<String>();
	}
	
	public List<ServiceEntity> search(String domainName,
			ServiceSearchEntity serviceSearch) {
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<ServiceEntity> services = new ArrayList<ServiceEntity>();
		List<Future<List<ServiceEntity>>> futureResults = new ArrayList<Future<List<ServiceEntity>>>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<ServiceEntity>> futureResult = serviceRepository.getServices(repo.getUrl(), serviceSearch);
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
						services.addAll(futureResults.get(i).get());
						i++;
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return services;
	}

}
