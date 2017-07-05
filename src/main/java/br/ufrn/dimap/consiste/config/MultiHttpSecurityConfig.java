package br.ufrn.dimap.consiste.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public AuthenticationService authenticationService;
		
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
	}
	
	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/api/*")
				.authorizeRequests()
				.antMatchers("/api/rdo/**", "/api/repository/**", "/api/domain/**", "/api/sync-search/**", "/api/async-search/**").hasRole("USER")
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf().disable()
				.httpBasic();
		}
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/domain/**", "/resource/**", "/service/**", "/observation/**", "/sparql/**", "/repository/**", "/qoc/**", "/location/**").hasRole("USER")
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/signin")
			.loginProcessingUrl("/authenticate")
			.defaultSuccessUrl("/")
			.failureUrl("/signin?unauthorize=true")
			.usernameParameter("username")
			.passwordParameter("password")
			.and()
			.logout()
			.logoutUrl("/signout")
			.logoutSuccessUrl("/signin?left=true");
	}
	
}
