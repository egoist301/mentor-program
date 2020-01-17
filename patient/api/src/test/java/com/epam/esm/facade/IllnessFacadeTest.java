package com.epam.esm.facade;

import com.epam.esm.config.RootConfig;
import com.epam.esm.converter.IllnessDtoConverter;
import com.epam.esm.dto.IllnessRequestDto;
import com.epam.esm.dto.IllnessResponseDto;
import com.epam.esm.exception.IllnessAlreadyExistException;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.service.IllnessService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        List<IllnessResponseDto> illnessResponseDtos =
                illnesses.stream().map(IllnessDtoConverter::convertToDto).collect(Collectors.toList());
        Mockito.when(illnessService.getAll()).thenReturn(illnesses);
        Assert.assertEquals(facade.getAll(), illnessResponseDtos);
        Mockito.verify(illnessService).getAll();
    }

    @Test
    public void get_setCorrectId_shouldBeIllnessResponseDto() {
        Mockito.when(illnessService.get(id)).thenReturn(illness);
        Assert.assertNotNull(facade.get(id));
        Mockito.verify(illnessService).get(id);
    }

    @Test
    public void get_setIncorrectId_shouldBeNull() {
        Mockito.when(illnessService.get(id)).thenReturn(null);
        Assert.assertNull(illnessService.get(id));
        Mockito.verify(illnessService).get(id);
    }

    @Test
    public void create_setCorrectIllnessRequest_shouldBeIllnessResponse() {
        Mockito.when(illnessService.isIllnessExist(illness.getName())).thenReturn(false);
        Mockito.when(illnessService.findByName(illness.getName())).thenReturn(illness);
        Assert.assertNotNull(facade.create(illnessRequestDto));
        Mockito.verify(illnessService).isIllnessExist(illness.getName());
        Mockito.verify(illnessService).create(illness);
    }

    @Test(expected = IllnessAlreadyExistException.class)
    public void create_setIncorrectIllnessRequest_shouldBeIllnessResponse() {
        Mockito.when(illnessService.isIllnessExist(illness.getName())).thenReturn(true);
        Assert.assertNotNull(facade.create(illnessRequestDto));
        Mockito.verify(illnessService).isIllnessExist(illness.getName());
    }
}
