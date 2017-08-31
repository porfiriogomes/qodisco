package br.ufrn.dimap.consiste.filter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.dimap.consiste.domain.DomainEntity;
import br.ufrn.dimap.consiste.topic.TopicEntity;

public interface FilterRepository  extends JpaRepository<FilterEntity, String>{

	@Query("SELECT id FROM FilterEntity")
	public List<String> getFilterNames();
	
	@Query("DELETE FROM FilterEntity f WHERE f.async_filter_topic = ?1")
	public void deleteByTopicName(String topicName);
	
	@Query("SELECT t FROM FilterEntity t WHERE t.async_filter_topic = ?1")
	public List<FilterEntity> findAllByTopic(String topicName);
	
}
