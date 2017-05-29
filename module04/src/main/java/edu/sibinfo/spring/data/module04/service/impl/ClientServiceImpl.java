package edu.sibinfo.spring.data.module04.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;

import edu.sibinfo.spring.data.module04.dao.ClientDao;
import edu.sibinfo.spring.data.module04.dao.PhoneType;
import edu.sibinfo.spring.data.module04.domain.Client;
import edu.sibinfo.spring.data.module04.domain.Phone;
import edu.sibinfo.spring.data.module04.domain.QClient;
import edu.sibinfo.spring.data.module04.service.ClientRegisteredEvent;
import edu.sibinfo.spring.data.module04.service.ClientService;

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
		clientDao.delete(client.getId()); // clientDao.delete(client) fails in non-transactional context 
	}

	@Override
	public Client findByPhone(String number) {
		return clientDao.findByPhone(number);
	}

	@Override
	public Client findByFamilyName(String familyName) {
		return clientDao.findByFamilyName(familyName);
	}

	@Transactional
	@Override
	public Client findByFamilyName(String familyName, Consumer<Client> consumer) {
		Client c = clientDao.findByFamilyName(familyName);
		consumer.accept(c);
		return c;
	}

	@Override
	public Iterable<Client> search(String charactersitics) {
		String[] chs = charactersitics.split("\\s+");
		String familyName = chs[0];
		BooleanExpression predicate = null; 
		QClient qClient = QClient.client;
		if (Character.isUpperCase(familyName.charAt(0))) {
			predicate = qClient.familyName.startsWith(familyName);
		} else {
			predicate = qClient.familyName.contains(familyName);
		}
		if (chs.length > 1) {
			String firstName = chs[1];
			if (Character.isUpperCase(firstName.charAt(0))) {
				predicate = predicate.and(qClient.firstName.startsWith(firstName));
			} else {
				predicate = predicate.and(qClient.firstName.contains(firstName));
			}
		}
		return clientDao.findAll(predicate);
	}
}
