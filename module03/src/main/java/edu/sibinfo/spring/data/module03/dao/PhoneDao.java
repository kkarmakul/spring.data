package edu.sibinfo.spring.data.module03.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sibinfo.spring.data.module03.domain.Client;
import edu.sibinfo.spring.data.module03.domain.Phone;

@Repository
public interface PhoneDao extends CrudRepository<Phone, Long> {

	List<Phone> findByClient(Client realClient);

}
