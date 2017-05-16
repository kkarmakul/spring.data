package edu.sibinfo.spring.data.module02.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.data.module02.AppRunner;
import edu.sibinfo.spring.data.module02.dao.ClientDao;
import edu.sibinfo.spring.data.module02.domain.Client;
import edu.sibinfo.spring.data.module02.service.impl.SmsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTests {
    @Autowired
    private ClientService service;
    @Autowired
    private ClientDao dao;
    @MockBean
    private SmsService smsService;
    @MockBean
    private AppRunner appRunner;
    
    @Test
    public void clientRegisters() {
        Client client = service.register("A", "B", "7");
        Client realClient = dao.findOne(client.getId());
        assertNotNull(realClient);
        assertEquals("A", realClient.getFirstName());
        assertEquals("B", realClient.getFamilyName());
        assertEquals("7", realClient.getMobile());
        
        ArgumentCaptor<ClientRegisteredEvent> captor = ArgumentCaptor.forClass(ClientRegisteredEvent.class); 
        verify(smsService).sendRegistrationNotification(captor.capture());
        assertSame(realClient, captor.getValue().getClient());
    }
}
