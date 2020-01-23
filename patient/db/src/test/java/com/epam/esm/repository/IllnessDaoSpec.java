package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.repository.config.ConnectionConfig;
import com.epam.esm.repository.entity.Illness;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ConnectionConfig.class, TestConfig.class})
@ActiveProfiles("dev")
public class IllnessDaoSpec {
    @Autowired
    private IllnessDao illnessDao;
    private Illness illness;
    private List<Illness> illnesses;

    @Before
    public void init() {
        illness = new Illness();
        illness.setId(1l);
        LocalDate localDate = DateFormatter.convertStringToDate("2020-01-17");
        illness.setCreateDate(localDate);
        illness.setUpdateDate(localDate);
        illness.setDescription("desc1");
        illness.setName("сифилис");
        illness.setChanceToDie(80);

        Illness illness = new Illness();
        illness.setId(2l);
        illness.setCreateDate(localDate);
        illness.setUpdateDate(localDate);
        illness.setDescription("desc2");
        illness.setName("аппендицит");
        illness.setChanceToDie(14);

        illnesses = new ArrayList<>();
        illnesses.add(this.illness);
        illnesses.add(illness);
    }

    @Test
    public void findById_setCorrectId_shouldBeReturnIllness() {
        assertEquals(illnessDao.findById(1l), Collections.singletonList(illness));
    }

    @Test
    public void findById_setCIncorrectId_shouldBeReturnEmptyList() {
        assertEquals(illnessDao.findById(4l), Collections.emptyList());
    }

    @Test
    public void create_setCorrectIllness_shouldBeCreated() {
        Illness illness = new Illness();
        illness.setId(3l);
        illness.setCreateDate(LocalDate.now());
        illness.setUpdateDate(LocalDate.now());
        illness.setDescription("desc3");
        illness.setName("name");
        illness.setChanceToDie(80);
        illnessDao.create(illness);
        assertEquals(illnessDao.findById(illness.getId()), Collections.singletonList(illness));
    }

    @Test
    public void delete_shouldBeDeleted() {
        illnessDao.delete(3l);
        assertEquals(illnessDao.findById(3l), Collections.emptyList());
    }

    @Test
    public void findByName_setCorrectName_shouldBeReturnIllness() {
        assertEquals(illnessDao.findByName(illness.getName()), Collections.singletonList(illness));
    }

    @Test
    public void findByName_setIncorrectName_shouldBeReturnEmptyList() {
        assertEquals(illnessDao.findByName("help"), Collections.emptyList());
    }

    @Test
    public void findByNameWithDifferentId_setCorrectNameAndIncorrectId_shouldBeReturnIllness() {
        assertEquals(illnessDao.findByNameWithDifferentId(illness.getName(), 2l), Collections.singletonList(illness));
    }

    @Test
    public void findByNameWithDifferentId_setCorrectNameCorrectId_shouldBeReturnEmptyList() {
        assertEquals(illnessDao.findByNameWithDifferentId("сифилис", 1l), Collections.emptyList());
    }

    @Test
    public void getAll_shouldBeReturnAll() {

        assertEquals(illnessDao.getAll().get(0), illnesses.get(0));
        assertEquals(illnessDao.getAll().get(1), illnesses.get(1));
    }

    @Test
    public void findByPatientId_ShouldBeReturnSet() {
        assertEquals(illnessDao.findByPatientId(1l), new LinkedHashSet<>(illnesses));
    }
}
