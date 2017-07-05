package br.ufrn.dimap.consiste.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;
	
	public AuthorityEntity getAuthority(String name) {
		return authorityRepository.findOneByName(name);
	}
	
}
