package com.epam.esm.repository;

import com.epam.esm.repository.config.ConnectionConfig;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.util.DateFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ConnectionConfig.class, TestConfig.class})
@ActiveProfiles("dev")
public class PatientDaoSpecTest {
    @Autowired
    private PatientDao patientDao;
    private Patient patient;
    private List<Patient> patients;
    private String idNumber;
    private Long id;

    @Before
    public void init() {

        LocalDate date = DateFormatter.convertStringToDate("2020-01-17");
        Set<Illness> illnesses = new LinkedHashSet<>();
        patients = new ArrayList<>();

        idNumber = "1234567890qwer";
        id = 1l;
        patient = new Patient();
        patient.setId(1l);
        patient.setFirstName("Yauheni");
        patient.setLastName("Filipovich");
        patient.setMiddleName("Aleksandrovich");
        patient.setIdentificationNumber("1234567890qwer");
        patient.setPhoneNumber(3221114);
        patient.setDateOfBirth(date);
        patient.setIllnesses(illnesses);
        patient.setCreateDate(date);
        patient.setUpdateDate(date);

        Patient firstPatient = new Patient();
        firstPatient.setId(2l);
        firstPatient.setFirstName("first1");
        firstPatient.setLastName("last1");
        firstPatient.setMiddleName("middle1");
        firstPatient.setIdentificationNumber("0987654321rewq");
        firstPatient.setPhoneNumber(7654321);
        firstPatient.setDateOfBirth(date);
        firstPatient.setIllnesses(new LinkedHashSet<>());
        firstPatient.setCreateDate(date);
        firstPatient.setUpdateDate(date);

        Illness firstIllness = new Illness();
        firstIllness.setId(1l);
        firstIllness.setName("сифилис");
        firstIllness.setDescription("desc1");
        firstIllness.setChanceToDie(80);
        firstIllness.setCreateDate(date);
        firstIllness.setUpdateDate(date);

        Illness secondIllness = new Illness();
        secondIllness.setId(2l);
        secondIllness.setName("аппендицит");
        secondIllness.setDescription("desc2");
        secondIllness.setChanceToDie(14);
        secondIllness.setCreateDate(date);
        secondIllness.setUpdateDate(date);

        illnesses.add(firstIllness);
        illnesses.add(secondIllness);
        patients.add(patient);
        patients.add(firstPatient);
    }

    @Test
    public void findById_setCorrectId_shouldReturnPatient() {
        Assert.assertEquals(patientDao.findById(id), patient);
    }
}