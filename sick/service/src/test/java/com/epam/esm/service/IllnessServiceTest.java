package com.epam.esm.service;

import com.epam.esm.dao.IllnessDao;
import com.epam.esm.entity.Illness;
import com.epam.esm.exception.EntityIsNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    private LocalDate date;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        illnessService = new IllnessService(illnessDao);
        date = LocalDate.now();
        id = 1L;
        name = "name";
        illness = new Illness();
        illness.setId(1L);
        illness.setName("name");
        illness.setChanceToDie(30);
        illness.setDescription("description");
        illness.setUpdateDate(date);
        illness.setUpdateDate(date);
        illnesses = createIllnesses();
    }

    @Test
    public void get_setCorrectId_shouldBeIllness() {
        when(illnessDao.findById(id)).thenReturn(illness);
        when(illnessDao.existsById(id)).thenReturn(true);
        assertEquals(illness, illnessService.findById(id));
    }

    @Test(expected = NoResultException.class)
    public void get_setIncorrectId_shouldBeSQLException() {
        when(illnessDao.findById(id)).thenThrow(NoResultException.class);
        when(illnessDao.existsById(id)).thenReturn(true);
        assertEquals(illness, illnessService.findById(id));
    }

    @Test(expected = EntityIsNotExistException.class)
    public void get_setIncorrectId_shouldBeException() {
        when(illnessDao.existsById(id)).thenReturn(false);
        assertEquals(illness, illnessService.findById(id));
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        when(illnessDao.findAll(1, 2)).thenReturn(illnesses);
        assertEquals(illnesses, illnessService.findAll(1, 2));

    }

    @Test
    public void findByName_setCorrectName_shouldBeReturnIllness() {
        when(illnessDao.findByName(name)).thenReturn(illness);
        assertEquals(illness, illnessService.findByName(name));
    }

    @Test(expected = NoResultException.class)
    public void findByName_setIncorrectName_shouldBeReturnException() {
        when(illnessDao.findByName(name)).thenThrow(NoResultException.class);
        assertEquals(illness, illnessService.findByName(name));
    }

    @Test
    public void findWidelyUsed_shouldBeReturnIllness() {
        when(illnessDao.findWidelyUsed()).thenReturn(illness);
        assertEquals(illness, illnessService.findWidelyUsed());
    }

    private List<Illness> createIllnesses() {
        Illness illness1 = new Illness();
        illness1.setId(1L);
        illness1.setName("name1");
        illness1.setChanceToDie(30);
        illness1.setDescription("description1");
        illness1.setUpdateDate(date);
        illness1.setUpdateDate(date);

        Illness illness2 = new Illness();
        illness2.setId(2L);
        illness2.setName("name2");
        illness2.setChanceToDie(50);
        illness2.setDescription("description2");
        illness2.setUpdateDate(date);
        illness2.setUpdateDate(date);
        return Arrays.asList(illness1, illness2);
    }
}
