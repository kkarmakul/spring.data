package edu.sibinfo.spring.data.module02.service;

import edu.sibinfo.spring.data.module02.domain.Client;

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
