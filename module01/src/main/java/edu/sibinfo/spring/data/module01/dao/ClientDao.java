package edu.sibinfo.spring.data.module01.dao;

import edu.sibinfo.spring.data.module01.domain.Client;

public interface ClientDao {
	void save(Client client);

    Client getById(Long id);
}
