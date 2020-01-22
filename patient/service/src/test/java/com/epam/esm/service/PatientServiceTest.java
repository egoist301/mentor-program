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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        when(patientDao.findById(id)).thenReturn(Collections.singletonList(patient));
        assertNotNull(patientService.get(id));
    }

    @Test
    public void get_setIncorrectId_shouldBeReturnNull() {
        when(patientDao.findById(id)).thenReturn(null);
        assertNull(patientService.get(id));
    }

    @Test
    public void getAll_shouldBeReturnAll() {
        String param = "";
        when(patientDao.getAll(param, param, param, param, param, param)).thenReturn(patients);
        assertEquals(patientService.getAll(param, param, param, param, param, param), patients);
    }

    @Test
    public void isPatientExist_setCorrectIdentificationNumber_shouldBeTrue() {
        when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(Collections.singletonList(patient));
        assertTrue(patientService.isPatientExist(idNumber));
    }

    @Test
    public void isPatientExist_setIncorrectIdentificationNumber_shouldBeFalse() {
        when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(null);
        assertFalse(patientService.isPatientExist(idNumber));
    }

    @Test
    public void isPatientExist_setCorrectId_shouldBeTrue() {
        when(patientDao.findById(id)).thenReturn(Collections.singletonList(patient));
        assertTrue(patientService.isPatientExist(id));
    }

    @Test
    public void isPatientExist_setIncorrectId_shouldBeFalse() {
        when(patientDao.findById(id)).thenReturn(null);
        assertFalse(patientService.isPatientExist(id));
    }

    @Test
    public void findByIdentificationNumber_setCorrectIdentificationNumber_shouldBeReturnPatient() {
        when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(Collections.singletonList(patient));
        assertNotNull(patientService.findByIdentificationNumber(idNumber));
    }

    @Test
    public void findByIdentificationNumber_setIncorrectIdentificationNumber_shouldBeReturnNull() {
        when(patientDao.findByIdentificationNumber(idNumber)).thenReturn(null);
        assertNull(patientService.findByIdentificationNumber(idNumber));
    }
}
