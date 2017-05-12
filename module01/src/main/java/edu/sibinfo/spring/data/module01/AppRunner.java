package edu.sibinfo.spring.data.module01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.sibinfo.spring.data.module01.domain.Client;
import edu.sibinfo.spring.data.module01.service.ClientService;

@Component
public class AppRunner implements ApplicationRunner {
	
	private final ClientService clientService;

	@Autowired
	public AppRunner(ClientService clientService) {
		this.clientService = clientService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		clientService.register("Luke", "Ford", "+79239889568");
		Client johnSmith = clientService.register("John", "Smith", "+79132354312");
		clientService.setPassword(johnSmith, "ad6123s%");
		clientService.register("Sam", "Bush", "+79239872348");		
	}
}
