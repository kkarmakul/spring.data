package edu.sibinfo.spring.data.module03.service;

import edu.sibinfo.spring.data.module03.domain.Client;

public class ClientRegisteredEvent {
	private final Client client;

	public ClientRegisteredEvent(Client client) {
		super();
		this.client = client;
	}

	public Client getClient() {
		return client;
	}  
}
