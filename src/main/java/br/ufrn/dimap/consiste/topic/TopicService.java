package br.ufrn.dimap.consiste.topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

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
	
}
