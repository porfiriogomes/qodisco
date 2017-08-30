package br.ufrn.dimap.consiste.filter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.dimap.consiste.domain.DomainEntity;

public interface FilterRepository  extends JpaRepository<FilterEntity, String>{

	@Query("SELECT id FROM FilterEntity")
	public List<String> getFilterNames();
	
}
