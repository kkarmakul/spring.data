package edu.sibinfo.spring.data.module03.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.data.module03.dao.ClientDao;
import edu.sibinfo.spring.data.module03.dao.PhoneType;
import edu.sibinfo.spring.data.module03.domain.Client;
import edu.sibinfo.spring.data.module03.domain.Phone;
import edu.sibinfo.spring.data.module03.service.impl.SmsService;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes=ClientServiceCustomContextTestConfig.class)
public class ClientServiceCustomContextTests {
    @Autowired
    private ClientService service;
    @Autowired
    private ClientDao dao;
    @Autowired
    private SmsService smsService;
    
    @Test
    public void clientRegisters() {
        clientRegistration("A", "B", "7");
    }

    @Test
    public void clientRegistersCyrillic() {
        clientRegistration("А", "Б", "+7");
    }

	private Client clientRegistration(String firstName, String familyName, String mobile) {
		Client client = service.register(firstName, familyName, mobile);
        assertEquals(1L, dao.count());
        Client realClient = dao.findOne(client.getId());
        assertNotNull(realClient);
        assertEquals(firstName, realClient.getFirstName());
        assertEquals(familyName, realClient.getFamilyName());
        
        List<Phone> phones = realClient.getPhones();
        assertEquals(1L, phones.size());
        checkPhone(mobile, realClient, phones.get(0), PhoneType.MOBILE);
        
        ArgumentCaptor<ClientRegisteredEvent> captor = ArgumentCaptor.forClass(ClientRegisteredEvent.class); 
        verify(smsService).sendRegistrationNotification(captor.capture());
        assertSame(client, captor.getValue().getClient());
        return realClient;
	}

	private void checkPhone(String number, Client client, Phone phone, PhoneType phoneType) {
		assertNotNull(phone);
        assertEquals(number, phone.getNumber());
        assertEquals(phoneType, phone.getPhoneType());
	}
	
	@Test 
	public void addPhonesAllTypes() {
		registerClientWith3Phones();
	}

	private Client registerClientWith3Phones() {
		String mobilePhone = "+701010101";
		Client client = clientRegistration("Три", "Телефона", mobilePhone);
		String homePhone = "+702020202";
		service.addPhone(client, homePhone, PhoneType.HOME);
		String officePhone = "+703030303";
		service.addPhone(client, officePhone, PhoneType.OFFICE);

		Client realClient = dao.findOne(client.getId());
        List<Phone> phones = realClient.getPhones();
        assertEquals(3L, phones.size());
        checkPhone(mobilePhone, client, phones.get(0), PhoneType.MOBILE);
        checkPhone(homePhone, client, phones.get(1), PhoneType.HOME);
        checkPhone(officePhone, client, phones.get(2), PhoneType.OFFICE);
        return client;
	}
	
	@Test
	public void registerAndDeleteClient() {
		Client client = registerClientWith3Phones();
		service.deleteClient(client);
		assertEquals(0L, dao.count());
	}
}
