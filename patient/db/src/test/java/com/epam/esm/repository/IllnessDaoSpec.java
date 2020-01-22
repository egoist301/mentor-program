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

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


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
        assertEquals(illnessDao.findById(1l), illness);
    }

    @Test
    public void findById_setCIncorrectId_shouldBeReturnNull() {
        assertNull(illnessDao.findById(4l));
    }

    @Test
    public void create_setCorrectIllness_shouldBeCreated() throws ParseException {
        Illness illness = new Illness();
        illness.setId(3l);
        illness.setCreateDate(DateFormatter.convertStringToDate("2020-01-17"));
        illness.setUpdateDate((DateFormatter.convertStringToDate("2020-01-17")));
        illness.setDescription("desc3");
        illness.setName("name");
        illness.setChanceToDie(80);
        illnessDao.create(illness);
        assertEquals(illnessDao.findById(illness.getId()), illness);
    }

    @Test
    public void delete_shouldBeDeleted() {
        illnessDao.delete(3l);
        assertNull(illnessDao.findById(3l));
    }

    @Test
    public void findByName_setCorrectName_shouldBeReturnIllness() {
        assertEquals(illnessDao.findByName(illness.getName()), illness);
    }

    @Test
    public void findByName_setIncorrectName_shouldBeReturnNull() {
        assertNull(illnessDao.findByName("help"));
    }

    @Test
    public void findByNameWithDifferentId_setCorrectNameAndIncorrectId_shouldBeReturnIllness() {
        assertEquals(illnessDao.findByNameWithDifferentId(illness.getName(), 2l), illness);
    }

    @Test
    public void findByNameWithDifferentId_setCorrectNameCorrectId_shouldBeReturnNull() {
        assertNull(illnessDao.findByNameWithDifferentId("сифилис", 1l));
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
