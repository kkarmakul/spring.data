package edu.sibinfo.spring.data.module02.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.sibinfo.spring.data.module02.AppRunner;
import edu.sibinfo.spring.data.module02.dao.ClientDao;
import edu.sibinfo.spring.data.module02.domain.Client;
import edu.sibinfo.spring.data.module02.service.impl.SmsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
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
        clientRegistration("A", "B", "7");
    }

    @Test
    public void clientRegistersCyrillic() {
        clientRegistration("А", "Б", "+7");
    }

	private void clientRegistration(String firstName, String familyName, String mobile) {
		Client client = service.register(firstName, familyName, mobile);
        assertThat(dao.count(), equalTo(1L));
        Client realClient = dao.findOne(client.getId());
        assertNotNull(realClient);
        assertEquals(firstName, realClient.getFirstName());
        assertEquals(familyName, realClient.getFamilyName());
        assertEquals(mobile, realClient.getMobile());
        
        ArgumentCaptor<ClientRegisteredEvent> captor = ArgumentCaptor.forClass(ClientRegisteredEvent.class); 
        verify(smsService).sendRegistrationNotification(captor.capture());
        assertSame(client, captor.getValue().getClient());
	}
}
