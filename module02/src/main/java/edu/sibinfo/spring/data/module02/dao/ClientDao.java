package edu.sibinfo.spring.data.module02.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sibinfo.spring.data.module02.domain.Client;

@Repository
public interface ClientDao extends CrudRepository<Client, Long> {

}
