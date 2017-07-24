package br.ufrn.dimap.consiste.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.authority.AuthorityService;
import br.ufrn.dimap.consiste.filter.FilterService;

@Service
public class UserService {
	
	private static final Logger LOGGER = Logger.getLogger(UserService.class); 

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;

	public boolean exists(String username) {
		return userRepository.findOneByUsername(username) != null;
	}
	
	public void create(UserEntity user) {

		if (user.getPermissions() == null)
			user.addPermission(authorityService.getAuthority("ROLE_USER"));			
			
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public UserEntity getUser(String username) {
		return userRepository.findOneByUsername(username);
	}
	
	public UserEntity getLoggedUser() {
		Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
		if (authenticatedUser == null) throw new AuthenticationCredentialsNotFoundException("The user is not logged");
		LOGGER.info(authenticatedUser.getPrincipal());
		UserDetails loggedUser = (UserDetails) authenticatedUser.getPrincipal();
		return getUser(loggedUser.getUsername());
	}
	
}
