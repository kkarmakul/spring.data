package edu.sibinfo.spring.data.module01.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.sibinfo.spring.data.module01.dao.ClientDao;
import edu.sibinfo.spring.data.module01.domain.Client;

@Repository
public class ClientDaoImpl implements ClientDao {
	private static final Logger log = LoggerFactory.getLogger(ClientDao.class);
	
	private AtomicLong lastId = new AtomicLong(0L); 
	private Map<Long, Client> clients = new HashMap<Long, Client>();
	
	public void save(Client client) {
		if (client.getId() == null) {
			client.setId(lastId.getAndIncrement());
			clients.put(client.getId(), client);
			log.info("Added {}. Total: {}", client, clients.size());
		} else {
			log.info("Saved {}. Total: {}", client, clients.size());
		}
	}

    @Override
    public Client getById(Long id) {
        return clients.get(id);
    }
}
