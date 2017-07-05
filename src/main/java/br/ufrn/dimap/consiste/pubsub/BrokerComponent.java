package br.ufrn.dimap.consiste.pubsub;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.moquette.interception.InterceptHandler;
import io.moquette.server.Server;
import io.moquette.server.config.MemoryConfig;

@Component
public class BrokerComponent {
	
	private static final Logger LOGGER = Logger.getLogger(BrokerComponent.class);
	
	@Autowired
	private Environment environment;
	
	private Server server;
	private MemoryConfig config;
	
	public void start() throws IOException {
		List<? extends InterceptHandler> userHandlers = Arrays.asList(new PublisherListener());
		
		config = new MemoryConfig(new Properties());
		config.setProperty("port", environment.getProperty("broker.server_port"));
		config.setProperty("websocket_port", environment.getProperty("broker.websocket_port"));
		config.setProperty("host", environment.getProperty("broker.host"));
		
		server = new Server();
		server.startServer(config, userHandlers);
		LOGGER.info("Broker started");
	}
	
	public void stop() {
		server.stopServer();
		LOGGER.info("Broker stopped");
	}
	
}
