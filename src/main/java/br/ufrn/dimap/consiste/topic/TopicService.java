package br.ufrn.dimap.consiste.topic;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dimap.consiste.utils.FusekiRepository;

@Service
public class TopicService {

	private static final Logger LOGGER = Logger.getLogger(FusekiRepository.class); 

	@Autowired
	private TopicRepository topicRepository;
	
	public TopicEntity getTopicByName(String topicName) {
		return topicRepository.findOne(topicName);
	}

	public void saveTopic(TopicEntity topic) {
		topicRepository.save(topic);
	}
	
	public boolean removeTopic(String topicName) {
		TopicEntity topicEntity = topicRepository.findOne(topicName);
		if (topicEntity != null){
			topicRepository.delete(topicName);
			return true;
		}
		return false;
	}

	public List<TopicEntity> getTopicsByDomain(String domainName) {
		return topicRepository.findAllByDomain(domainName);
	}
	
	public void updateLastDate(double date, String topicName) {
		TopicEntity topic = topicRepository.findOne(topicName);
		topic.setLastQuerySendDate(date);
		topicRepository.save(topic);
	}
	
	public void updateLastValue(double value, String topicName) {
		TopicEntity topic = topicRepository.findOne(topicName);
		topic.setLastQuerySendValue(value);
		topicRepository.save(topic);	}
	
}
