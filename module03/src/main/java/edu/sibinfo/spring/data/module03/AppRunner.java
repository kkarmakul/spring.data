package edu.sibinfo.spring.data.module03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.sibinfo.spring.data.module03.dao.PhoneType;
import edu.sibinfo.spring.data.module03.domain.Client;
import edu.sibinfo.spring.data.module03.service.ClientService;

@Component
public class AppRunner implements ApplicationRunner {
	Logger log = LoggerFactory.getLogger(AppRunner.class);
	private final ClientService clientService;

	@Autowired
	public AppRunner(ClientService clientService) {
		this.clientService = clientService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		clientService.register("Luke", "Ford", "+79239889568");
		Client client = clientService.register("John", "Smith", "+79132354312");
		clientService.register("Sam", "Bush", "+79239872348");
		clientService.addPhone(client, "+79138439343", PhoneType.OFFICE);
		
		final Client johnSmith = clientService.findByFamilyName("Smith", 
				c -> c.getPhones().stream().forEach(p -> log.info("{} {} is accsessible by: {}({})", 
				c.getFamilyName(), c.getFirstName(), p.getNumber(), p.getPhoneType())));
		clientService.setPassword(johnSmith, "ad6123s%");
		clientService.deleteClient(johnSmith);
	}
}
