package br.ufrn.dimap.consiste.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.dimap.consiste.domain.DomainEntity;
import br.ufrn.dimap.consiste.repository.RepositoryEntity;
import br.ufrn.dimap.consiste.topic.TopicEntity;
import br.ufrn.dimap.consiste.user.UserEntity;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	private APIService apiService;
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public ResponseEntity<Void> addUser(@RequestBody UserEntity user) {
		return (!apiService.addUser(user))
			? new ResponseEntity<>(HttpStatus.CONFLICT)
			: new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/rdo", method=RequestMethod.POST)
	public ResponseEntity<Void> addRdo(@RequestBody DomainEntity domain) {
		if (apiService.addDomain(domain))
			return new ResponseEntity<>(HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value="/rdo", method=RequestMethod.GET)
	public List<DomainEntity> listRdos() {
		return apiService.getDomains();
	}
	
	@RequestMapping(value="/rdo", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateRdo(@RequestBody DomainEntity domain) {
		return (apiService.updateDomain(domain))
			? new ResponseEntity<>(HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/repository", method=RequestMethod.POST)
	public ResponseEntity<Void> addRepository(@RequestBody RepositoryEntity repository) {
		return (apiService.addRepository(repository))
			? new ResponseEntity<>(HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	// Return all the repositories owned by the logged user, and only them
	// Because the repositories owned by other users are not interesting to the logged user
	@RequestMapping(value="/repository", method=RequestMethod.GET)
	public List<RepositoryEntity> listMyRepositories(){
		return apiService.getMyRepositories();
	}
	
	@RequestMapping(value="/repository", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateRepository(@RequestBody RepositoryEntity repositoryEntity) {
		return (apiService.updateRepository(repositoryEntity))
			? new ResponseEntity<>(HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/repository", method=RequestMethod.DELETE)
	public ResponseEntity<Void> removeRepository(@RequestParam("id") Integer id) {
		return (apiService.removeRepository(id))
			? new ResponseEntity<>(HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/sync-search", method=RequestMethod.GET, produces={"application/xml", "application/json"})
	public ResponseEntity<String> synchronousSearch(@RequestParam("domain") String domainName, @RequestHeader("query") String query, @RequestHeader(name="Accept", defaultValue="application/xml") String acceptedFormat) {
		return new ResponseEntity<String>(apiService.search(domainName, query, acceptedFormat), HttpStatus.OK);
	}
	
	@RequestMapping(value="/async-search", method=RequestMethod.GET)
	public ResponseEntity<TopicEntity> asynchronousSearch(@RequestHeader("query") String query, @RequestParam("domain") String domain) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSS");
		Date date = new Date();
		
		// Creating an unique topic name
		String topicName = "qodisco"+auth.getName()+dateFormat.format(date);
				
		TopicEntity topic = apiService.asyncSearch(query, topicName, domain);
		return new ResponseEntity<TopicEntity>(topic, HttpStatus.OK);
	}
	
	@RequestMapping(value="/async-search", method=RequestMethod.DELETE)
	public ResponseEntity<Void> ubsubscribe(@RequestParam("topic") String topic) {
		return (apiService.unsubscribe(topic))
			? new ResponseEntity<>(HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/record", method=RequestMethod.POST)
	public ResponseEntity<Void> updateRecord(@RequestParam("domain") String domain, @RequestBody String query) {
		return (apiService.save(domain, query))
			? new ResponseEntity<>(HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
