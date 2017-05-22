package edu.sibinfo.spring.data.module02;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.data.module02.dao.ClientDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Application02Tests {
    @Autowired
    private ClientDao dao;

    @Test
    public void contextLoads() {
        assertEquals(3, dao.count());
    }   
}
