package edu.sibinfo.spring.data.module04.service;

import java.util.function.Consumer;

import edu.sibinfo.spring.data.module04.dao.PhoneType;
import edu.sibinfo.spring.data.module04.domain.Client;
import edu.sibinfo.spring.data.module04.domain.Phone;

public interface ClientService {
	Client register(String firstName, String familyName, String phone);

	void setPassword(Client client, String password);

	Phone addPhone(Client client, String number, PhoneType phoneType);
	
	void deleteClient(Client client);
	
	Client findByPhone(String number);

	Client findByFamilyName(String familyName);
	
	Client findByFamilyName(String familyName, Consumer<Client> consumer);
}
