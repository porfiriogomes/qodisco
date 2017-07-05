package br.ufrn.dimap.consiste.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer>{

	public AuthorityEntity findOneByName(String name);
	
}
