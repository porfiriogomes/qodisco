package br.ufrn.dimap.consiste.repository;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.domain.DomainEntity;
import br.ufrn.dimap.consiste.domain.DomainService;
import br.ufrn.dimap.consiste.user.UserService;

@Service
public class RepositoryService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DomainService domainService;
	
	@Autowired
	private RepositoryRepository repositoryRepository;
	
	@Autowired
	private Environment environment;
	
	public boolean save(RepositoryEntity repository) {
		repository.setUser(userService.getLoggedUser());
		
		// Setting Default as one of the repository domains
		Set<DomainEntity> domains = repository.getDomains();
		DomainEntity defaultDomain = domainService.getDomainByName("Default");

		if (domains==null) 
			domains = new HashSet<DomainEntity>();
		
		domains.add(defaultDomain);
		repository.setDomains(domains);
		repository.setRepositoryUnavailableTimeEntity(new RepositoryUnavailableTimeEntity(new Date(), Long.valueOf(0)));
		
		return (repositoryRepository.save(repository)!= null) ? true : false;
	}
	
	public RepositoryEntity getRepository(Integer id) {
		return repositoryRepository.getOne(id);
	}
	
	public List<DomainEntity> getDomains() {
		List<DomainEntity> domains = domainService.getDomains();
		return domains;
	}
	
	public boolean delete(Integer id) {
		RepositoryEntity repository = this.getRepository(id);
		if (repository != null && repository.getUser().equals(userService.getLoggedUser())) {
			repositoryRepository.delete(repository);
			return true;
		} else {
			return false;
		}
	}
	
	public List<RepositoryEntity> getRepositoriesFromLoggedUser() {
		List<RepositoryEntity> repositories = repositoryRepository.findAllByUser(userService.getLoggedUser().getId());

		// Updating repositories availability times	
		for (RepositoryEntity repository : repositories) {
			repository = this.updateAvailability(repository);
		}
		
		return repositories;
	}

	public List<RepositoryEntity> getAvailableRepositoriesByDomain(String domainName) {
		// Check if the repositories are available
		return this.getAvailableRepositories(this.getRepositoriesByDomain(domainName));
	}


	public RepositoryEntity getRandomAvailableRepository(String domainName) {
		List<RepositoryEntity> repositories = this.getAvailableRepositories(this.getRepositoriesByDomain(domainName));
		if (repositories==null || repositories.size()==0){
			return null;
		} else {
			Random r = new Random();
			int chosenIndex = r.nextInt(repositories.size());
			return repositories.get(chosenIndex);
		}
	}

	protected List<RepositoryEntity> getRepositoriesByDomain(String domainName) {
		List<RepositoryEntity> repositories = repositoryRepository.findAllByDomain(domainName);
		return repositories;
	}
	
	protected RepositoryEntity updateAvailability(RepositoryEntity repository) {
		Long unavailableTime = repository.getRepositoryUnavailableTimeEntity().getTime();
		
		int timeout = Integer.valueOf(environment.getProperty("repository.timeout"));
		int maxUnavailableTime = Integer.valueOf(environment.getProperty("repository.unavailableTime"));
		
		if (unavailableTime.equals(0)){
			if (pingUrl(repository.getUrl(), timeout)) {
				return repository;
			} else {
				repository.getRepositoryUnavailableTimeEntity().setLastCheck(new Date());
				repository.getRepositoryUnavailableTimeEntity().setTime(Long.valueOf(maxUnavailableTime));
				repositoryRepository.save(repository);
			}
		} else {
			Date currentTime = new Date();
			Date lastCheckTime = repository.getRepositoryUnavailableTimeEntity().getLastCheck();
			
//			System.out.println("Current Time: " + currentTime.getTime());
//			System.out.println("Last Check Time: " + lastCheckTime.getTime());
			
			repository.getRepositoryUnavailableTimeEntity().setLastCheck(currentTime);
			
			if((currentTime.getTime() - lastCheckTime.getTime()) >= unavailableTime) {
				boolean isAvailable = pingUrl(repository.getUrl(), timeout);
//				System.out.println("Repository is available? " + isAvailable);
				if(!isAvailable) {
					repository.getRepositoryUnavailableTimeEntity().setTime(Long.valueOf(maxUnavailableTime));
				} else {
					repository.getRepositoryUnavailableTimeEntity().setTime(Long.valueOf(0));
				}
			}
			repositoryRepository.save(repository);
		}
		return repository;
	}
	
	protected boolean pingUrl(String url, int timeout) {
		// Otherwise an exception may be thrown on invalid SSL certificates.
		url = url.replaceFirst("^https", "http");
	
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("HEAD");
			int responseCode = connection.getResponseCode();
			return (200 <= responseCode && responseCode <= 399);
		} catch (Exception e) {
			return false;
		}
	}
	
	protected List<RepositoryEntity> getAvailableRepositories(List<RepositoryEntity> repositories) {
		List<RepositoryEntity> availableRepositories = new ArrayList<RepositoryEntity>();
		for (RepositoryEntity repository : repositories) {
			if (this.updateAvailability(repository).getRepositoryUnavailableTimeEntity().getTime()==0)
				availableRepositories.add(repository);
		}
		return availableRepositories;
	}
	
}
