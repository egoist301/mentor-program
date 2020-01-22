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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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
        when(illnessDao.findById(id)).thenReturn(Collections.singletonList(illness));
        assertTrue(illnessService.isIllnessExist(id));
    }

    @Test
    public void isIllnessExist_setIncorrectId_shouldBeFalse() {
        when(illnessDao.findById(id)).thenReturn(null);
        assertFalse(illnessService.isIllnessExist(id));
    }

    @Test
    public void get_setCorrectId_shouldBeIllness() {
        when(illnessDao.findById(id)).thenReturn(Collections.singletonList(illness));
        assertNotNull(illnessService.get(id));
    }

    @Test
    public void get_setIncorrectId_shouldBeNull() {
        when(illnessDao.findById(id)).thenReturn(null);
        assertNull(illnessService.get(id));
    }

    @Test
    public void isAnyIllnessExistWithName_setCorrectNameAndId_ShouldBeTrue() {
        when(illnessDao.findByNameWithDifferentId(name, id)).thenReturn(Collections.singletonList(illness));
        assertTrue(illnessService.isAnyIllnessExistWithName(id, name));
    }

    @Test
    public void isAnyIllnessExistWithName_setIncorrectNameAndId_shouldBeFalse() {
        when(illnessDao.findByNameWithDifferentId(name, id)).thenReturn(null);
        assertFalse(illnessService.isAnyIllnessExistWithName(id, name));
    }

    @Test
    public void isIllnessExist_setCorrectName_shouldBeIllness() {
        when(illnessDao.findByName(name)).thenReturn(Collections.singletonList(illness));
        assertTrue(illnessService.isIllnessExist(name));
    }

    @Test
    public void isIllnessExist_setIncorrectName_shouldBeFalse() {
        when(illnessDao.findByName(name)).thenReturn(null);
        assertFalse(illnessService.isIllnessExist(name));
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        when(illnessDao.getAll()).thenReturn(illnesses);
        assertEquals(illnessService.getAll(), illnesses);

    }

    @Test
    public void findByPatientId_setCorrectId_shouldBeReturnIllnesses() {
        when(illnessDao.findByPatientId(id)).thenReturn(new LinkedHashSet<>(illnesses));
        assertEquals(illnessService.findByPatientId(id), new LinkedHashSet<>(illnesses));
    }

    @Test
    public void findByPatientId_setIncorrectId_shouldBeReturnEmptySet() {
        when(illnessDao.findByPatientId(id)).thenReturn(new LinkedHashSet<>());
        assertEquals(illnessService.findByPatientId(id), new LinkedHashSet<>());
    }
}
