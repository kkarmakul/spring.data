package edu.sibinfo.spring.data.module03.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sibinfo.spring.data.module03.domain.Client;

@Repository
public interface ClientDao extends CrudRepository<Client, Long> {

}
