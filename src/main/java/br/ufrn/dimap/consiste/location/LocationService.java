package br.ufrn.dimap.consiste.location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.domain.DomainService;
import br.ufrn.dimap.consiste.repository.RepositoryEntity;
import br.ufrn.dimap.consiste.repository.RepositoryService;

@Service
public class LocationService {

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private DomainService domainService;
	
	@Autowired
	private LocationRepository locationRepository;
	
	public List<String> getAllFixedStructures(String domainName) {
		String url = repositoryService.getRandomAvailableRepository(domainName).getUrl();
		return locationRepository.getFixedStructures(url);
	}
	
	public List<String> getAllLocations(String domainName) {
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);

		List<Future<List<String>>> futureResults = new ArrayList<Future<List<String>>>();
		List<String> locations = new ArrayList<String>();
		
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<String>> futureResult = locationRepository.getLocations(repo.getUrl());
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
						locations.addAll(futureResults.get(i).get());
						i++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return locations;
	}
	
	public List<LocationEntity> getAllLocationsFullDescription(String domainName) {
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		
		List<Future<List<LocationEntity>>> futureResults = new ArrayList<Future<List<LocationEntity>>>();
		List<LocationEntity> locations = new ArrayList<LocationEntity>();
				
		if(repositories.size()>0){
			for(RepositoryEntity repo : repositories){
				Future<List<LocationEntity>> futureResult = locationRepository.getLocationsFull(repo.getUrl());
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
						locations.addAll(futureResults.get(i).get());
						i++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
				
		return locations;
	}

	public List<String> getDomainNames() {
		return domainService.getDomainNames();
	}

	public void create(String domainName, LocationEntity location) {
		RepositoryEntity repo = repositoryService.getRandomAvailableRepository(domainName);
		locationRepository.addLocation(location, repo.getUrl(), domainName);
	}
	
}
