package edu.sibinfo.spring.data.module02.service;

import edu.sibinfo.spring.data.module02.domain.Client;

public interface ClientService {
	Client register(String firstName, String familyName, String phone);

	void setPassword(Client client, String password);
}
