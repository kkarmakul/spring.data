package edu.sibinfo.spring.data.module01.service;

import edu.sibinfo.spring.data.module01.domain.Client;

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
