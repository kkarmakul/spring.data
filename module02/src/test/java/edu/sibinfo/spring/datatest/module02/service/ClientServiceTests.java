package edu.sibinfo.spring.datatest.module02.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.data.module02.dao.ClientDao;
import edu.sibinfo.spring.data.module02.domain.Client;
import edu.sibinfo.spring.data.module02.service.ClientRegisteredEvent;
import edu.sibinfo.spring.data.module02.service.ClientService;
import edu.sibinfo.spring.data.module02.service.impl.SmsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=ClientServiceTestConfig.class)
public class ClientServiceTests {
	
    @Autowired
    private ClientService service;
    @Autowired
    private ClientDao dao;
    @Autowired
    private SmsService smsService;		
    
    private Client savedClient;
    
    @Test
    public void registerAscii() {
    	register("Joe", "Stone", "+1129038744");
    }

    @Test
    public void registerCyrillic() {
    	register("Леонид", "Ильич", "+70950000001");
    }

    private void register(String firstName, String lastName, String mobile) {
    	when(dao.count()).thenReturn(1L);
    	when(dao.save(Mockito.any(Client.class))).thenAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				savedClient = invocation.getArgumentAt(0, Client.class);
				savedClient.setId(0L);
				return null;
			}
		});   	
    	
        Client client = service.register(firstName, lastName, mobile);
        assertEquals(1, dao.count());
        
    	when(dao.findOne(anyLong())).thenReturn(savedClient);
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
