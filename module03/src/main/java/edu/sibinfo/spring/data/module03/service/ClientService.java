package edu.sibinfo.spring.data.module03.service;

import edu.sibinfo.spring.data.module03.dao.PhoneType;
import edu.sibinfo.spring.data.module03.domain.Client;
import edu.sibinfo.spring.data.module03.domain.Phone;

public interface ClientService {
	Client register(String firstName, String familyName, String phone);

	void setPassword(Client client, String password);

	Phone addPhone(Client client, String number, PhoneType phoneType);
	
	void deleteClient(Client client);
}
