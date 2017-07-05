package br.ufrn.dimap.consiste.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.user.UserService;

@Service
public class AuthenticationService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = userService.getUser(username);
		if (user == null)
			throw new UsernameNotFoundException("Username not found");
		return user;
	}
	
}
