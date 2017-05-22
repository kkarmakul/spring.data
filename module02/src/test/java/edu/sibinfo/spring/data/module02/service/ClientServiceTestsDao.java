package edu.sibinfo.spring.data.module02.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.data.module02.dao.ClientDao;
import edu.sibinfo.spring.data.module02.domain.Client;
import edu.sibinfo.spring.data.module02.service.impl.SmsService;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes={ClientServiceTestsDaoConfig.class})
public class ClientServiceTestsDao {
	
	@Autowired
    private ClientService service;
    @Autowired
    private ClientDao dao;
    @Autowired
    private SmsService smsService;		
     
    @Test
    public void registerAscii() {
    	register("Joe", "Stone", "+1129038744");
    }

    @Test
    public void registerCyrillic() {
    	register("Леонид", "Ильич", "+70950000001");
    }

    private void register(String firstName, String lastName, String mobile) {   	
        Client client = service.register(firstName, lastName, mobile);
        assertEquals(1, dao.count());
        
        Client realClient = dao.findOne(client.getId());
        assertNotNull(realClient);
        assertEquals(firstName, realClient.getFirstName());
        assertEquals(lastName, realClient.getFamilyName());
        assertEquals(mobile, realClient.getMobile());
        
        ArgumentCaptor<ClientRegisteredEvent> captor = ArgumentCaptor.forClass(ClientRegisteredEvent.class); 
        verify(smsService).sendRegistrationNotification(captor.capture());
        assertSame(client, captor.getValue().getClient());
    }
}
