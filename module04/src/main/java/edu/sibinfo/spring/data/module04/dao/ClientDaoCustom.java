package edu.sibinfo.spring.data.module04.dao;

import edu.sibinfo.spring.data.module04.domain.Client;

public interface ClientDaoCustom {
	Iterable<Client> search(String charactersitics);
}
