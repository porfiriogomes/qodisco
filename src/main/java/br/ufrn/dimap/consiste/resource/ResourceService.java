package br.ufrn.dimap.consiste.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.domain.DomainService;
import br.ufrn.dimap.consiste.location.LocationService;
import br.ufrn.dimap.consiste.repository.RepositoryEntity;
import br.ufrn.dimap.consiste.repository.RepositoryService;

@Service
public class ResourceService {
	
	@Autowired
	private DomainService domainService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private LocationService locationService;

	@Autowired
	private ResourceRepository resourceRepository;
	
	public List<String> getDomainNames() {
		return domainService.getDomainNames();
	}
	
	public List<String> getDeviceTypes(String domainName) {
		RepositoryEntity repo = repositoryService.getRandomAvailableRepository(domainName);
		if (repo!=null)
			return resourceRepository.getDeviceTypes(repo.getUrl());
		return new ArrayList<String>();
	}
	
	public List<String> getLocations(String domainName) {
		return locationService.getAllLocations(domainName);
	}
	
	public void create(String domainName, ResourceEntity resource) {
		resourceRepository.addResource(repositoryService.getRandomAvailableRepository(domainName).getUrl(), resource, domainName);
	}

	public List<String> getObservedProperties(String domainName) {
		String url = repositoryService.getRandomAvailableRepository(domainName).getUrl();
		return resourceRepository.getObservedProperties(url);
	}
	
	public List<ResourceEntity> search(String domainName,
			ResourceSearchEntity resourceSearch) {
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<ResourceEntity> resources = new ArrayList<ResourceEntity>();
		List<Future<List<ResourceEntity>>> futureResults = new ArrayList<Future<List<ResourceEntity>>>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<ResourceEntity>> futureResult = resourceRepository.search(resourceSearch, repo.getUrl());
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
	
}
