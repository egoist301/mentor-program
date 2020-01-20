package com.epam.esm.service;

import com.epam.esm.repository.PatientDao;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.entity.Patient;
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
import java.util.Set;

public class PatientServiceTest {
    @Mock
    private PatientDao patientDao;
    @InjectMocks
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
        LocalDate date = LocalDate.now();
        patient.setDateOfBirth(date);
        patient.setIllnesses(illnesses);
        patient.setUpdateDate(date);
        patient.setCreateDate(date);

        Patient firstPatient = new Patient();
        firstPatient.setId(1l);
        firstPatient.setFirstName("first1");
        firstPatient.setLastName("last1");
        firstPatient.setMiddleName("middle1");
        firstPatient.setIdentificationNumber("0987654321rewq");
        firstPatient.setPhoneNumber(7654321);
        firstPatient.setDateOfBirth(date);
        firstPatient.setIllnesses(new LinkedHashSet<>());
        firstPatient.setUpdateDate(date);
        firstPatient.setCreateDate(date);

        Illness firstIllness = new Illness();
        firstIllness.setId(1l);
        firstIllness.setName("name1");
        firstIllness.setChanceToDie(30);
        firstIllness.setDescription("description1");
        firstIllness.setUpdateDate(date);
        firstIllness.setCreateDate(date);

        Illness secondIllness = new Illness();
        secondIllness.setId(2l);
        secondIllness.setName("name2");
        secondIllness.setChanceToDie(50);
        secondIllness.setDescription("description2");
        secondIllness.setUpdateDate(date);
        secondIllness.setCreateDate(date);

        illnesses.add(firstIllness);
        illnesses.add(secondIllness);
        patients.add(firstPatient);
        patients.add(patient);
    }

    @Test
    public void get_setCorrectId_shouldBeReturnPatient() {
        Mockito.when(patientDao.findById(id)).thenReturn(patient);
        Assert.assertNotNull(patientService.get(id));
    }

    @Test
    public void get_setIncorrectId_shouldBeReturnNull() {
        Mockito.when(patientDao.findById(id)).thenReturn(null);
        Assert.assertNull(patientService.get(id));
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        String param = "";
        Mockito.when(patientDao.getAll(param, param, param, param, param, param)).thenReturn(patients);
        Assert.assertEquals(patientService.getAll(param, param, param, param, param, param), patients);
    }

    @Test
    public void isPatientExist_setCorrectIdentificationNumber_shouldBeTrue() {
        Mockito.when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(patient);
        Assert.assertTrue(patientService.isPatientExist(idNumber));
    }

    @Test
    public void isPatientExist_setIncorrectIdentificationNumber_shouldBeFalse() {
        Mockito.when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(null);
        Assert.assertFalse(patientService.isPatientExist(idNumber));
    }

    @Test
    public void isPatientExist_setCorrectId_shouldBeTrue() {
        Mockito.when(patientDao.findById(id)).thenReturn(patient);
        Assert.assertTrue(patientService.isPatientExist(id));
    }

    @Test
    public void isPatientExist_setIncorrectId_shouldBeFalse() {
        Mockito.when(patientDao.findById(id)).thenReturn(null);
        Assert.assertFalse(patientService.isPatientExist(id));
    }

    @Test
    public void isAnyPatientExistWithIdentificationNumber_setCorrectParam_shouldBeTrue() {
        Mockito.when(patientDao.findByIdentificationNumberWithDifferentId(id, idNumber)).thenReturn(patient);
        Assert.assertTrue(patientService.isAnyPatientExistWithIdentificationNumber(id, idNumber));
    }

    @Test
    public void isAnyPatientExistWithIdentificationNumber_setIncorrectParam_shouldBeFalse() {
        Mockito.when(patientDao.findByIdentificationNumberWithDifferentId(id, idNumber)).thenReturn(null);
        Assert.assertFalse(patientService.isAnyPatientExistWithIdentificationNumber(id, idNumber));
    }

    @Test
    public void findByIdentificationNumber_setCorrectIdentificationNumber_shouldBeReturnPatient() {
        Mockito.when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(patient);
        Assert.assertNotNull(patientService.findByIdentificationNumber(idNumber));
    }

    @Test
    public void findByIdentificationNumber_setIncorrectIdentificationNumber_shouldBeReturnNull() {
        Mockito.when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(null);
        Assert.assertNull(patientService.findByIdentificationNumber(idNumber));
    }
}
