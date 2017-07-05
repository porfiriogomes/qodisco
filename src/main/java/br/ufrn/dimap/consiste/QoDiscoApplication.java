package br.ufrn.dimap.consiste;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import br.ufrn.dimap.consiste.pubsub.BrokerComponent;

@SpringBootApplication
@EnableAsync
public class QoDiscoApplication {
	
	private static Environment environment;

	@Autowired
	public void setEnvironment(Environment environment) {
		QoDiscoApplication.environment = environment;
	}

	public static void main(String[] args) throws IOException {
		SpringApplication application = new SpringApplication(QoDiscoApplication.class);
		final ApplicationContext context = application.run(args);
		
		String serviceOption = environment.getProperty("broker.service_option");
		
		if (serviceOption.equals("moquette")) {			
			BrokerComponent broker = context.getBean(BrokerComponent.class);
			broker.start();
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					broker.stop();
				}
			});
		}
		
	}
	
}
