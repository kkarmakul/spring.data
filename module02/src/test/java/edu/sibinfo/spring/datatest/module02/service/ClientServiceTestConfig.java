package edu.sibinfo.spring.datatest.module02.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import edu.sibinfo.spring.data.module02.EncoderConfiguration;
import edu.sibinfo.spring.data.module02.dao.ClientDao;
import edu.sibinfo.spring.data.module02.service.impl.ClientServiceImpl;
import edu.sibinfo.spring.data.module02.service.impl.SmsService;

@TestConfiguration
@SpringBootConfiguration
@Import({ClientServiceImpl.class, EncoderConfiguration.class})
public class ClientServiceTestConfig {
    @MockBean
    private ClientDao dao;
    @MockBean
    private SmsService smsService;
}
