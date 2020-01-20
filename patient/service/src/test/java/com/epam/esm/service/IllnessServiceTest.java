package com.epam.esm.service;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class IllnessServiceTest {
    @Mock
    private IllnessDao illnessDao;
    @InjectMocks
    private IllnessService illnessService;
    private List<Illness> illnesses;
    private Illness illness;
    private Long id;
    private String name;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        illnessService = new IllnessService(illnessDao);
        illnesses = new ArrayList<>();

        id = 1l;
        name = "name";
        illness = new Illness();
        illness.setId(1l);
        illness.setName("name");
        illness.setChanceToDie(30);
        illness.setDescription("description");
        LocalDate date = LocalDate.now();
        illness.setUpdateDate(date);
        illness.setUpdateDate(date);

        Illness illness1 = new Illness();
        illness1.setId(1l);
        illness1.setName("name1");
        illness1.setChanceToDie(30);
        illness1.setDescription("description1");
        illness1.setUpdateDate(date);
        illness1.setUpdateDate(date);

        Illness illness2 = new Illness();
        illness2.setId(2l);
        illness2.setName("name2");
        illness2.setChanceToDie(50);
        illness2.setDescription("description2");
        illness2.setUpdateDate(date);
        illness2.setUpdateDate(date);

        illnesses.add(illness1);
        illnesses.add(illness2);
    }

    @Test
    public void isIllnessExist_setCorrectId_shouldBeTrue() {
        Mockito.when(illnessDao.findById(id)).thenReturn(illness);
        Assert.assertTrue(illnessService.isIllnessExist(id));
    }

    @Test
    public void isIllnessExist_setIncorrectId_shouldBeFalse() {
        Mockito.when(illnessDao.findById(id)).thenReturn(null);
        Assert.assertFalse(illnessService.isIllnessExist(id));
    }

    @Test
    public void get_setCorrectId_shouldBeIllness() {
        Mockito.when(illnessDao.findById(id)).thenReturn(illness);
        Assert.assertNotNull(illnessService.get(id));
    }

    @Test
    public void get_setIncorrectId_shouldBeNull() {
        Mockito.when(illnessDao.findById(id)).thenReturn(null);
        Assert.assertNull(illnessService.get(id));
    }

    @Test
    public void isAnyIllnessExistWithName_setCorrectNameAndId_ShouldBeTrue() {
        Mockito.when(illnessDao.findByNameWithDifferentId(name, id)).thenReturn(illness);
        Assert.assertTrue(illnessService.isAnyIllnessExistWithName(id, name));
    }

    @Test
    public void isAnyIllnessExistWithName_setIncorrectNameAndId_shouldBeFalse() {
        Mockito.when(illnessDao.findByNameWithDifferentId(name, id)).thenReturn(null);
        Assert.assertFalse(illnessService.isAnyIllnessExistWithName(id, name));
    }

    @Test
    public void isIllnessExist_setCorrectName_shouldBeIllness() {
        Mockito.when(illnessDao.findByName(name)).thenReturn(illness);
        Assert.assertTrue(illnessService.isIllnessExist(name));
    }

    @Test
    public void isIllnessExist_setIncorrectName_shouldBeFalse() {
        Mockito.when(illnessDao.findByName(name)).thenReturn(null);
        Assert.assertFalse(illnessService.isIllnessExist(name));
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        Mockito.when(illnessDao.getAll()).thenReturn(illnesses);
        Assert.assertEquals(illnessService.getAll(), illnesses);

    }

    @Test
    public void findByPatientId_setCorrectId_shouldBeReturnIllnesses() {
        Mockito.when(illnessDao.findByPatientId(id)).thenReturn(new LinkedHashSet<>(illnesses));
        Assert.assertEquals(illnessService.findByPatientId(id), new LinkedHashSet<>(illnesses));
    }

    @Test
    public void findByPatientId_setIncorrectId_shouldBeReturnEmptySet() {
        Mockito.when(illnessDao.findByPatientId(id)).thenReturn(new LinkedHashSet<>());
        Assert.assertEquals(illnessService.findByPatientId(id), new LinkedHashSet<>());
    }
}
