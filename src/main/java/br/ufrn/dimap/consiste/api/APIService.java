package br.ufrn.dimap.consiste.api;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.sparql.util.ResultSetUtils;

import br.ufrn.dimap.consiste.domain.DomainEntity;
import br.ufrn.dimap.consiste.domain.DomainService;
import br.ufrn.dimap.consiste.filter.FilterEntity;
import br.ufrn.dimap.consiste.filter.FilterService;
import br.ufrn.dimap.consiste.repository.RepositoryEntity;
import br.ufrn.dimap.consiste.repository.RepositoryService;
import br.ufrn.dimap.consiste.sparql.SparqlService;
import br.ufrn.dimap.consiste.topic.TopicEntity;
import br.ufrn.dimap.consiste.topic.TopicService;
import br.ufrn.dimap.consiste.user.UserEntity;
import br.ufrn.dimap.consiste.user.UserService;

@Service
public class APIService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DomainService domainService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private FilterService filterService;

	@Autowired
	private APIRepository apiRepository;
	
	@Autowired
	private SparqlService sparqlService;
	
	@Autowired
	private Environment environment;
	
	public boolean addUser(UserEntity user){
		if (userService.exists(user.getUsername()))
			return false;
		user.setId(null);
		userService.create(user);
		return true;
	}
	
	public boolean addDomain(DomainEntity domain) {
		return domainService.save(domain);
	}
	
	public List<DomainEntity> getDomains() {
		return domainService.getDomains();
	}
	
	public boolean updateDomain(DomainEntity domain) {
		DomainEntity originalDomain = domainService.getDomainByName(domain.getName());
		if(originalDomain != null) {
			if(originalDomain.getUser().equals(userService.getLoggedUser())){
				originalDomain.setOntologyUri(domain.getName() != null ? domain.getName() : originalDomain.getName());;
				return domainService.save(domain);
			}
		}
		return false;
	}
	
	public boolean addRepository(RepositoryEntity repository) {
		repository.setId(null);
		return repositoryService.save(repository);
	}
	
	public boolean updateRepository(RepositoryEntity repository) {
		RepositoryEntity oldRepository = repositoryService.getRepository(repository.getId());
		if (oldRepository != null) {
			if (oldRepository.getUser().equals(userService.getLoggedUser())) {
				oldRepository.setDomains(repository.getDomains() != null ? repository.getDomains() : oldRepository.getDomains());
				oldRepository.setOperations(repository.getOperations() != null ? repository.getOperations() : oldRepository.getOperations());
				oldRepository.setUrl(repository.getUrl() != null ? repository.getUrl() : oldRepository.getUrl());
				return repositoryService.save(oldRepository);
			}
		}
		return false;
	}
	
	public boolean removeRepository(Integer repositoryId) {
		RepositoryEntity repository = repositoryService.getRepository(repositoryId);
		if (repository != null) {
			return repositoryService.delete(repositoryId);
		}
		return false;
	}
	
	public List<RepositoryEntity> getMyRepositories() {
		return repositoryService.getRepositoriesFromLoggedUser();
	}
	
	public String search(String domainName, String query, String acceptedFormat) {
		List<RepositoryEntity> repositories = repositoryService.getAvailableRepositoriesByDomain(domainName);
		List<Future<ResultSet>> futureResults = new ArrayList<Future<ResultSet>>();
		List<ResultSet> results = new ArrayList<ResultSet>();
		
		if (repositories.size() > 0) {
			for (RepositoryEntity repo : repositories) {
				Future<ResultSet> futureResult = apiRepository.search(repo.getUrl(), query);
				futureResults.add(futureResult);
			}
			int i = 0;
			while(true) {
				if (i == futureResults.size()) break;
				if (!(futureResults.get(i).isDone())){
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						results.add(futureResults.get(i).get());
					} catch (Exception e) {
						e.printStackTrace();
					}
					i++;
				}
			}
		}	
		if (results.size() > 0) {
			ResultSet result = ResultSetUtils.union(results.toArray(new ResultSet[results.size()]));
		
			if (acceptedFormat.equals("application/xml")){
				return ResultSetFormatter.asXMLString(result);
			} else {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ResultSetFormatter.outputAsJSON(outputStream, result);
				String jsonResult = new String(outputStream.toByteArray());
				return new String(jsonResult);
			}
		} else {
			return "";
		}
	}

	public TopicEntity asyncSearch(String query, String topicName, String domainName) {
		String serverPort = environment.getProperty("broker.server_port");
		String host = environment.getProperty("broker.host");
		String brokerAddress = String.format("tcp://%s:%s", host, serverPort);
		
		DomainEntity domain = domainService.getDomainByName(domainName);
		TopicEntity topic = new TopicEntity(topicName, query, domain, brokerAddress, userService.getLoggedUser());
		topicService.saveTopic(topic);
		return topic;
	}
	
	public TopicEntity asyncSearchFilter(String query, String filter, String topicName, String domainName) {
		String serverPort = environment.getProperty("broker.server_port");
		String host = environment.getProperty("broker.host");
		String brokerAddress = String.format("tcp://%s:%s", host, serverPort);
		
		DomainEntity domain = domainService.getDomainByName(domainName);
		
		List<FilterEntity> filters = filterService.createFilter(filter);
		
		TopicEntity topic = new TopicEntity(topicName, query, domain, brokerAddress, userService.getLoggedUser(), filters);

		topicService.saveTopic(topic);
		return topic;
	}
	
	public boolean unsubscribe(String topic) {
		TopicEntity topicEntity = topicService.getTopicByName(topic);
		
		if (topicEntity!=null && topicEntity.getUser().equals(userService.getLoggedUser())) {			
			return topicService.removeTopic(topic);
		}
		return false;
	}

	public boolean save(String domainName, String query) {
		RepositoryEntity repository = repositoryService.getRandomAvailableRepository(domainName);
		if (repository != null) {
			apiRepository.updateRequest(repository.getUrl(), query, domainName);
			return true;
		} else {
			return false;
		}
	}
		
}
