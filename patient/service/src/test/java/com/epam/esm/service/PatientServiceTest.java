package com.epam.esm.service;

import com.epam.esm.repository.PatientDao;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.entity.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ContextConfiguration(classes = Config.class)
public class PatientServiceTest {
    @Mock
    private PatientDao patientDao;
    private PatientService patientService;
    private Set<Illness> illnesses;
    private Patient patient;
    private List<Patient> patients;
    private Long id;
    private String idNumber;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        patientService = new PatientService(patientDao);
        illnesses = new LinkedHashSet<>();
        patients = new ArrayList<>();

        idNumber = "1234567890qwer";
        id = 1l;
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
        patient.setUpdateDate(new Date());

        Patient patient1 = new Patient();
        patient.setId(1l);
        patient.setFirstName("first1");
        patient.setLastName("last1");
        patient.setMiddleName("middle1");
        patient.setIdentificationNumber("0987654321rewq");
        patient.setPhoneNumber(7654321);
        patient.setDateOfBirth(new Date());
        patient.setIllnesses(new LinkedHashSet<>());
        patient.setUpdateDate(new Date());
        patient.setUpdateDate(new Date());

        Illness illness1 = new Illness();
        illness1.setId(1l);
        illness1.setName("name1");
        illness1.setChanceToDie(30);
        illness1.setDescription("description1");
        illness1.setUpdateDate(new Date());
        illness1.setUpdateDate(new Date());

        Illness illness2 = new Illness();
        illness2.setId(2l);
        illness2.setName("name2");
        illness2.setChanceToDie(50);
        illness2.setDescription("description2");
        illness2.setUpdateDate(new Date());
        illness2.setUpdateDate(new Date());

        illnesses.add(illness1);
        illnesses.add(illness2);
        patients.add(patient1);
        patients.add(patient);
    }

    @Test
    public void get_setCorrectId_shouldBeReturnPatient() {
        Mockito.when(patientDao.findById(id)).thenReturn(patient);
        Assert.assertNotNull(patientService.get(id));
        Mockito.verify(patientDao).findById(id);
    }

    @Test
    public void get_setIncorrectId_shouldBeReturnNull() {
        Mockito.when(patientDao.findById(id)).thenReturn(null);
        Assert.assertNull(patientService.get(id));
        Mockito.verify(patientDao).findById(id);
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        String param = "";
        Mockito.when(patientDao.getAll(param, param, param, param, param, param)).thenReturn(patients);
        Assert.assertEquals(patientService.getAll(param, param, param, param, param, param), patients);
        Mockito.verify(patientDao).getAll(param, param, param, param, param, param);
    }

    @Test
    public void isPatientExist_setCorrectIdentificationNumber_shouldBeTrue() {
        Mockito.when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(patient);
        Assert.assertTrue(patientService.isPatientExist(idNumber));
        Mockito.verify(patientDao).findByIdentificationNumber(idNumber);
    }

    @Test
    public void isPatientExist_setIncorrectIdentificationNumber_shouldBeFalse() {
        Mockito.when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(null);
        Assert.assertFalse(patientService.isPatientExist(idNumber));
        Mockito.verify(patientDao).findByIdentificationNumber(idNumber);
    }

    @Test
    public void isPatientExist_setCorrectId_shouldBeTrue() {
        Mockito.when(patientDao.findById(id)).thenReturn(patient);
        Assert.assertTrue(patientService.isPatientExist(id));
        Mockito.verify(patientDao).findById(id);
    }

    @Test
    public void isPatientExist_setIncorrectId_shouldBeFalse() {
        Mockito.when(patientDao.findById(id)).thenReturn(null);
        Assert.assertFalse(patientService.isPatientExist(id));
        Mockito.verify(patientDao).findById(id);
    }

    @Test
    public void isAnyPatientExistWithIdentificationNumber_setCorrectParam_shouldBeTrue() {
        Mockito.when(patientDao.findByIdentificationNumberWithDifferentId(id, idNumber)).thenReturn(patient);
        Assert.assertTrue(patientService.isAnyPatientExistWithIdentificationNumber(id, idNumber));
        Mockito.verify(patientDao).findByIdentificationNumberWithDifferentId(id, idNumber);
    }

    @Test
    public void isAnyPatientExistWithIdentificationNumber_setIncorrectParam_shouldBeFalse() {
        Mockito.when(patientDao.findByIdentificationNumberWithDifferentId(id, idNumber)).thenReturn(null);
        Assert.assertFalse(patientService.isAnyPatientExistWithIdentificationNumber(id, idNumber));
        Mockito.verify(patientDao).findByIdentificationNumberWithDifferentId(id, idNumber);
    }

    @Test
    public void findByIdentificationNumber_setCorrectIdentificationNumber_shouldBeReturnPatient() {
        Mockito.when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(patient);
        Assert.assertNotNull(patientService.findByIdentificationNumber(idNumber));
        Mockito.verify(patientDao).findByIdentificationNumber(idNumber);
    }

    @Test
    public void findByIdentificationNumber_setIncorrectIdentificationNumber_shouldBeReturnNull() {
        Mockito.when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(null);
        Assert.assertNull(patientService.findByIdentificationNumber(idNumber));
        Mockito.verify(patientDao).findByIdentificationNumber(idNumber);
    }
}
