package br.ufrn.dimap.consiste.topic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, String>{

	@Query("SELECT t FROM TopicEntity t WHERE t.domain.name = ?1")
	List<TopicEntity> findAllByDomain(String domainName);
	
	@Async
	@Modifying(clearAutomatically=true)
    @Query("UPDATE TopicEntity t SET t.lastQuerySendDate = ?2 WHERE t.topic= ?1 ")
    void updateTopicLastDate(String topicId, double date);
	
	@Async
	@Modifying(clearAutomatically=true)
    @Query("UPDATE TopicEntity t SET t.lastQuerySendValue = ?2 WHERE t.topic= ?1 ")
    void updateTopicLastValue(String topicId, double value);

}
