package br.ufrn.dimap.consiste.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextProvider implements ApplicationContextAware{

	private static ApplicationContext context;
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
}
