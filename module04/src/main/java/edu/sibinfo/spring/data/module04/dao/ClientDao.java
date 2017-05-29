package edu.sibinfo.spring.data.module04.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sibinfo.spring.data.module04.domain.Client;

@Repository
public interface ClientDao extends CrudRepository<Client, Long>, 
	QueryDslPredicateExecutor<Client> 
{
	@Query(nativeQuery=true, value="SELECT c.* from client c JOIN phone p ON c.id = p.client_id AND p.phone_number = ?1")
	Client findByPhone(String number);
	
	Client findByFamilyName(String familyName);
}
