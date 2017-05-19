package edu.sibinfo.spring.data.module02.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.data.module02.EncoderConfiguration;
import edu.sibinfo.spring.data.module02.dao.ClientDao;
import edu.sibinfo.spring.data.module02.domain.Client;
import edu.sibinfo.spring.data.module02.service.impl.ClientServiceImpl;
import edu.sibinfo.spring.data.module02.service.impl.SmsService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceNoDaoTests {
  @Autowired
  private ClientService service;
  @Autowired
  private SmsService smsService;
  
  @Configuration
  @Import({ClientServiceImpl.class, EncoderConfiguration.class})
  public static class Config {
      @MockBean
      private ClientDao dao;
      @MockBean
      private SmsService smsService;
  }
    
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
        assertEquals(firstName, client.getFirstName());
        assertEquals(familyName, client.getFamilyName());
        assertEquals(mobile, client.getMobile());
        
        ArgumentCaptor<ClientRegisteredEvent> captor = ArgumentCaptor.forClass(ClientRegisteredEvent.class); 
        verify(smsService).sendRegistrationNotification(captor.capture());
        assertSame(client, captor.getValue().getClient());
	}
}
