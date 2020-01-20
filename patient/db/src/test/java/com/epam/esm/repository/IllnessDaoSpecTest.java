package com.epam.esm.repository;

import com.epam.esm.repository.config.ConnectionConfig;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.util.DateFormatter;
import org.junit.Assert;
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


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ConnectionConfig.class, TestConfig.class})
@ActiveProfiles("dev")
public class IllnessDaoSpecTest {
    @Autowired
    private IllnessDao illnessDao;
    private Illness illness;
    private List<Illness> illnesses;

    @Before
    public void init() throws ParseException {
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
        Assert.assertEquals(illnessDao.findById(1l), illness);
    }

    @Test
    public void findById_setCIncorrectId_shouldBeReturnNull() {
        Assert.assertNull(illnessDao.findById(4l));
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
        Assert.assertEquals(illnessDao.findById(illness.getId()), illness);
    }

    @Test
    public void delete_shouldBeDeleted() {
        illnessDao.delete(3l);
        Assert.assertNull(illnessDao.findById(3l));
    }

    @Test
    public void findByName_setCorrectName_shouldBeReturnIllness() {
        Assert.assertEquals(illnessDao.findByName(illness.getName()), illness);
    }

    @Test
    public void findByName_setIncorrectName_shouldBeReturnNull() {
        Assert.assertNull(illnessDao.findByName("help"));
    }

    @Test
    public void findByNameWithDifferentId_setCorrectNameAndIncorrectId_shouldBeReturnIllness() {
        Assert.assertEquals(illnessDao.findByNameWithDifferentId(illness.getName(), 2l), illness);
    }

    @Test
    public void findByNameWithDifferentId_setCorrectNameCorrectId_shouldBeReturnNull() {
        Assert.assertNull(illnessDao.findByNameWithDifferentId("сифилис", 1l));
    }

    @Test
    public void getAll_shouldBeReturnAll() {

        Assert.assertEquals(illnessDao.getAll().get(0), illnesses.get(0));
        Assert.assertEquals(illnessDao.getAll().get(1), illnesses.get(1));
    }

    @Test
    public void findByPatientId_ShouldBeReturnSet() {
        Assert.assertEquals(illnessDao.findByPatientId(1l), new LinkedHashSet<>(illnesses));
    }
}
