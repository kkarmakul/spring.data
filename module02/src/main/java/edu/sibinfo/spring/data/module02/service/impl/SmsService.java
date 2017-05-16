package edu.sibinfo.spring.data.module02.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import edu.sibinfo.spring.data.module02.domain.Client;
import edu.sibinfo.spring.data.module02.service.ClientRegisteredEvent;

@Service
public class SmsService {
	private static final Logger log = LoggerFactory.getLogger(SmsService.class);

	@EventListener
	public void sendRegistrationNotification(ClientRegisteredEvent event) {
		Client client  = event.getClient();
		log.info("{} : \"{}, you were registered\"", 
				client.getMobile(), client.getFirstName());
	}
}
