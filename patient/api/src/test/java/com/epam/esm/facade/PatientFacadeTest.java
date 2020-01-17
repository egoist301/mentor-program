package com.epam.esm.facade;

import com.epam.esm.config.RootConfig;
import com.epam.esm.converter.PatientDtoConverter;
import com.epam.esm.dto.IllnessRequestDto;
import com.epam.esm.dto.PatientRequestDto;
import com.epam.esm.dto.PatientResponseDto;
import com.epam.esm.exception.PatientAlreadyExistException;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.service.IllnessService;
import com.epam.esm.service.PatientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ContextConfiguration(classes = RootConfig.class)
public class PatientFacadeTest {
    @Mock
    private PatientService patientService;
    @Mock
    private IllnessService illnessService;
    @InjectMocks
    private PatientFacade facade;
    private List<Patient> patients;
    private Patient patient;
    private PatientRequestDto patientRequestDto;
    private Set<IllnessRequestDto> illnesses;
    private Long id;
    private String idNumber;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        facade = new PatientFacade(patientService, illnessService);
        patient = new Patient();
        patient.setIdentificationNumber(idNumber);
        illnesses = new LinkedHashSet<>();
        patients = new ArrayList<>();
        id = 1l;
        patient.setId(id);
        idNumber = "1234567890qwer";
        patientRequestDto = new PatientRequestDto();
        patientRequestDto.setIdentificationNumber(idNumber);
        patientRequestDto.setIllnesses(illnesses);
    }

    @Test
    public void get_setCorrectId_shouldBeReturnPatient() {
        Mockito.when(patientService.get(id)).thenReturn(patient);
        Assert.assertNotNull(facade.get(id));
        Mockito.verify(patientService).get(id);
    }

    @Test
    public void get_setIncorrectId_shouldBeReturnNull() {
        Mockito.when(patientService.get(id)).thenReturn(null);
        Assert.assertNull(facade.get(id));
        Mockito.verify(patientService).get(id);
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        String param = "";
        List<PatientResponseDto> patientResponseDtos =
                patients.stream().map(PatientDtoConverter::convertToDto).collect(Collectors.toList());

        Mockito.when(patientService.getAll(param, param, param, param, param, param)).thenReturn(patients);

        Assert.assertEquals(facade.getAll(param, param, param, param, param, param), patientResponseDtos);

        Mockito.verify(patientService).getAll(param, param, param, param, param, param);
    }

    @Test(expected = PatientAlreadyExistException.class)
    public void create_setIncorrectPatient_shouldBeReturnException() {
        Mockito.when(patientService.isPatientExist(idNumber)).thenReturn(true);
        Assert.assertNotNull(facade.create(patientRequestDto));
    }

    @Test
    public void create_setCorrectPatient_shouldBeReturnResponseDto() {
        Mockito.when(patientService.isPatientExist(idNumber)).thenReturn(false);
        Mockito.when(patientService.findByIdentificationNumber(idNumber)).thenReturn(patient);
        Assert.assertNotNull(facade.create(patientRequestDto));
    }
}
