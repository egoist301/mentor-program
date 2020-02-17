package com.epam.esm.dao;

import com.epam.esm.config.TestConnectionConfig;
import com.epam.esm.entity.Illness;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = TestConnectionConfig.class)
@SpringBootTest
public class IllnessDaoSpec {
    @Autowired
    private IllnessDao illnessDao;

    //@Autowired
    //private Flyway flyway;

    private Illness illness;

    @Before
    public void setUp() throws Exception {
        LocalDate localDate = LocalDate.parse("2018-11-06");
        illness = new Illness();
        illness.setId(1l);
        illness.setDescription("desc1");
        illness.setName("name1");
        illness.setCreateDate(localDate);
        illness.setUpdateDate(localDate);
        illness.setChanceToDie(80);
        //  flyway.migrate();
    }

    @After
    public void tearDown() {
        //flyway.clean();
    }

    @Test
    public void findById_setCorrectId_shouldBeReturnIllness() {
        assertEquals(illnessDao.findById(1l), illness);
    }

    @Test
    public void findById_setCIncorrectId_shouldBeReturnEmptyList() {
        assertEquals(illnessDao.findById(4l), Collections.emptyList());
    }

}
