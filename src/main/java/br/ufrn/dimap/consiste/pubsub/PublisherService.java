package br.ufrn.dimap.consiste.pubsub;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.topic.TopicEntity;

@Service
public class PublisherService {
	
	private static final Logger LOGGER = Logger.getLogger(PublisherService.class); 
	
	@Autowired
	private Environment environment;

	public void sendMessage(TopicEntity topicEntity, String messageContent) {
		try {
			
			String serverPort = environment.getProperty("broker.server_port");
			String host = environment.getProperty("broker.host");
			Integer qos = Integer.valueOf(environment.getProperty("broker.publisher.qos"));
			
			String brokerAddress = String.format("tcp://%s:%s", host, serverPort);
			
			MqttClient publisher = new MqttClient(brokerAddress, MqttClient.generateClientId(), new MemoryPersistence());
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			publisher.connect(connOpts);
			
			LOGGER.info("Publishing Mqtt message");
			MqttMessage message = new MqttMessage(messageContent.getBytes());
			message.setQos(qos);
			
			publisher.publish(topicEntity.getTopic(), message);
			LOGGER.info("Message published");
			
			publisher.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
