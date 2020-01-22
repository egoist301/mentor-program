package com.epam.esm.facade;

import com.epam.esm.config.RootConfig;
import com.epam.esm.dto.IllnessRequestDto;
import com.epam.esm.exception.EntityIsAlreadyExistException;
import com.epam.esm.exception.EntityIsNotExistException;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.service.IllnessService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = RootConfig.class)
public class IllnessFacadeTest {
    @Mock
    private IllnessRequestDto illnessRequestDto;
    @Mock
    private Illness illness;
    @Mock
    private IllnessService illnessService;
    private List<Illness> illnesses;
    private Long id;
    @InjectMocks
    private IllnessFacade facade;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        facade = new IllnessFacade(illnessService);
        illnesses = new ArrayList<>();
        id = 1l;
        illness = new Illness();
        illnessRequestDto = new IllnessRequestDto();
        illnessRequestDto.setName("name");
        illnessRequestDto.setChanceToDie(30);
        illnessRequestDto.setDescription("description");


        illness.setId(1l);
        illness.setName("name");
        illness.setDescription("description");
        illness.setChanceToDie(30);
        LocalDate date = LocalDate.now();
        illness.setCreateDate(date);
        illness.setUpdateDate(date);

        Illness firstIllness = new Illness();
        firstIllness.setId(1l);
        firstIllness.setName("name1");
        firstIllness.setDescription("description1");
        firstIllness.setChanceToDie(30);
        firstIllness.setCreateDate(date);
        firstIllness.setUpdateDate(date);

        Illness secondIllness = new Illness();
        secondIllness.setId(2l);
        secondIllness.setName("name2");
        secondIllness.setChanceToDie(50);
        secondIllness.setDescription("description2");
        secondIllness.setCreateDate(date);
        secondIllness.setUpdateDate(date);

        illnesses.add(firstIllness);
        illnesses.add(secondIllness);
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        when(illnessService.getAll()).thenReturn(illnesses);
        assertNotNull(facade.getAll());
    }

    @Test(expected = EntityIsNotExistException.class)
    public void get_setCorrectId_shouldBeIllnessResponseDto() {
        when(illnessService.get(id)).thenReturn(illness);
        assertNotNull(facade.get(id));
    }

    @Test
    public void get_setIncorrectId_shouldBeNull() {
        when(illnessService.get(id)).thenReturn(null);
        assertNull(illnessService.get(id));
    }

    @Test
    public void create_setCorrectIllnessRequest_shouldBeIllnessResponse() {
        when(illnessService.isIllnessExist(illness.getName())).thenReturn(false);
        when(illnessService.findByName(illness.getName())).thenReturn(illness);
        assertNotNull(facade.create(illnessRequestDto));
    }

    @Test(expected = EntityIsAlreadyExistException.class)
    public void create_setIncorrectIllnessRequest_shouldBeIllnessResponse() {
        when(illnessService.isIllnessExist(illness.getName())).thenReturn(true);
        when(illnessService.findByName(illness.getName())).thenReturn(illness);
        assertNotNull(facade.create(illnessRequestDto));
    }
}
