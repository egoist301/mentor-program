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
import java.util.Date;
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
    private Long id;
    private String idNumber;
    private Set<Illness> illnesses;
    private Illness illness;
    private Illness secondIllness;
    private Illness firstIllness;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        facade = new PatientFacade(patientService, illnessService);
        Set<IllnessRequestDto> illnessRequestDtos = new LinkedHashSet<>();
        patients = new ArrayList<>();
        illnesses = new LinkedHashSet<>();

        id = 1l;
        idNumber = "1234567890qwer";
        patientRequestDto = new PatientRequestDto();
        patientRequestDto.setIdentificationNumber(idNumber);
        patientRequestDto.setIllnesses(illnessRequestDtos);
        patientRequestDto.setDateOfBirth("2000-01-01");

        illness = new Illness();
        illness.setId(1l);
        illness.setName("name1");
        illness.setDescription("description1");
        illness.setChanceToDie(30);
        illness.setCreateDate(new Date());
        illness.setUpdateDate(new Date());

        IllnessRequestDto firstIllnessRequestDto = new IllnessRequestDto();
        firstIllnessRequestDto.setName("name1");
        firstIllnessRequestDto.setChanceToDie(30);
        firstIllnessRequestDto.setDescription("description1");

        IllnessRequestDto secondIllnessRequestDto = new IllnessRequestDto();
        secondIllnessRequestDto.setName("name2");
        secondIllnessRequestDto.setChanceToDie(50);
        secondIllnessRequestDto.setDescription("description2");

        illnessRequestDtos.add(firstIllnessRequestDto);
        illnessRequestDtos.add(secondIllnessRequestDto);

        patient = new Patient();
        patient.setId(1l);
        patient.setFirstName("first");
        patient.setLastName("last");
        patient.setMiddleName("middle");
        patient.setIdentificationNumber("1234567890qwer");
        patient.setPhoneNumber(1234567);
        patient.setDateOfBirth(new Date());
        patient.setIllnesses(illnesses);
        patient.setUpdateDate(new Date());
        patient.setCreateDate(new Date());

        Patient firstPatient = new Patient();
        firstPatient.setId(2l);
        firstPatient.setFirstName("first1");
        firstPatient.setLastName("last1");
        firstPatient.setMiddleName("middle1");
        firstPatient.setIdentificationNumber("0987654321rewq");
        firstPatient.setPhoneNumber(7654321);
        firstPatient.setDateOfBirth(new Date());
        firstPatient.setIllnesses(new LinkedHashSet<>());
        firstPatient.setUpdateDate(new Date());
        firstPatient.setCreateDate(new Date());

        firstIllness = new Illness();
        firstIllness.setId(1l);
        firstIllness.setName("name1");
        firstIllness.setChanceToDie(30);
        firstIllness.setDescription("description1");
        firstIllness.setUpdateDate(new Date());
        firstIllness.setCreateDate(new Date());

        secondIllness = new Illness();
        secondIllness.setId(2l);
        secondIllness.setName("name2");
        secondIllness.setChanceToDie(50);
        secondIllness.setDescription("description2");
        secondIllness.setCreateDate(new Date());
        secondIllness.setUpdateDate(new Date());

        illnesses.add(firstIllness);
        illnesses.add(secondIllness);
        patients.add(firstPatient);
        patients.add(patient);
    }

    @Test
    public void get_setCorrectId_shouldBeReturnPatient() {
        Mockito.when(patientService.get(id)).thenReturn(patient);
        Assert.assertNotNull(facade.get(id));
    }

    @Test
    public void get_setIncorrectId_shouldBeReturnNull() {
        Mockito.when(patientService.get(id)).thenReturn(null);
        Assert.assertNull(facade.get(id));
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        String param = "";
        List<PatientResponseDto> patientResponseDtos =
                patients.stream().map(PatientDtoConverter::convertToDto).collect(Collectors.toList());

        Mockito.when(patientService.getAll(param, param, param, param, param, param)).thenReturn(patients);

        Assert.assertNotNull(facade.getAll(param, param, param, param, param, param));
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
        Mockito.when(illnessService.findByName(firstIllness.getName())).thenReturn(firstIllness);
        Mockito.when(illnessService.findByName(secondIllness.getName())).thenReturn(secondIllness);

        Assert.assertNotNull(facade.create(patientRequestDto));
    }


}
