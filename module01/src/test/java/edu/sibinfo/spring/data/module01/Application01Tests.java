package edu.sibinfo.spring.data.module01;

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

import edu.sibinfo.spring.data.module01.dao.ClientDao;
import edu.sibinfo.spring.data.module01.domain.Client;
import edu.sibinfo.spring.data.module01.service.ClientRegisteredEvent;
import edu.sibinfo.spring.data.module01.service.ClientService;
import edu.sibinfo.spring.data.module01.service.impl.SmsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Application01Tests {
    @Autowired
    private ClientService service;
    @Autowired
    private ClientDao dao;
    @MockBean
    private SmsService smsService;
    
    @Test
    public void contextLoads() {
    }

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
