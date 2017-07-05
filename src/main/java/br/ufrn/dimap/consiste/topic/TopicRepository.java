package br.ufrn.dimap.consiste.topic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, String>{

	@Query("SELECT t FROM TopicEntity t WHERE t.domain.name = ?1")
	List<TopicEntity> findAllByDomain(String domainName);

}
