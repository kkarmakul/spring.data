package edu.sibinfo.spring.data.module03.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import edu.sibinfo.spring.data.module03.dao.ClientDao;
import edu.sibinfo.spring.data.module03.dao.PhoneType;
import edu.sibinfo.spring.data.module03.domain.Client;
import edu.sibinfo.spring.data.module03.domain.Phone;
import edu.sibinfo.spring.data.module03.service.ClientRegisteredEvent;
import edu.sibinfo.spring.data.module03.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientDao clientDao;
	private final MessageDigest encoder;
	private final ApplicationEventPublisher publisher;

	@Autowired
	public ClientServiceImpl(ClientDao clientDao, MessageDigest encoder, ApplicationEventPublisher publisher) {
		super();
		this.clientDao = clientDao;
		this.encoder = encoder;
		this.publisher = publisher;
	}

	@Override
	public Client register(String firstName, String familyName, String mobile) {
		Client client = new Client(familyName, firstName);
		clientDao.save(client);
		Phone phone = addPhone(client, mobile, PhoneType.MOBILE);
		publisher.publishEvent(new ClientRegisteredEvent(client, phone));
		return client;
	}
	
	@Override
	public Phone addPhone(Client client, String number, PhoneType phoneType) {
		Phone phone = new Phone(number, phoneType);
		client.addPhone(phone);
		clientDao.save(client);
		return phone;
	}

	@Override
	public void setPassword(Client client, String password) {
		client.setPassword(encoder.digest(password.getBytes(StandardCharsets.UTF_8)));
		clientDao.save(client);		
	}

	@Override
	public void deleteClient(Client client) {
		clientDao.delete(clientDao.findOne(client.getId()));
	}

	@Override
	public Client findByPhone(String number) {
		return clientDao.findByPhone(number);
	}
}
