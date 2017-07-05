package br.ufrn.dimap.consiste.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	public UserEntity findOneByUsername(String username);
	
}
