package br.ufrn.dimap.consiste.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.user.UserService;

@Service
public class DomainService {

	@Autowired
	private DomainRepository domainRepository;
	
	@Autowired
	private UserService userService;
	
	public boolean save(DomainEntity domain){
		domain.setUser(userService.getLoggedUser());
		return (domainRepository.save(domain)!=null) ? true : false;
	}
	
	public List<DomainEntity> getDomains() {
		return domainRepository.findAll();
	}
	
	public DomainEntity getDomainByName(String domainName) {
		return domainRepository.findOne(domainName);
	}
	
	public List<String> getDomainNames() {
		return domainRepository.getDomainNames();
	}
	
}
