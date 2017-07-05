package br.ufrn.dimap.consiste.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<DomainEntity, String>{

	@Query("SELECT name FROM DomainEntity")
	public List<String> getDomainNames();
	
}
