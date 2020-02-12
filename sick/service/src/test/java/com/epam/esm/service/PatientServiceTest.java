package com.epam.esm.service;

import com.epam.esm.dao.DoctorDao;
import com.epam.esm.dao.IllnessDao;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Illness;
import com.epam.esm.exception.EntityIsNotExistException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.when;

public class PatientServiceTest {
    @Mock
    private DoctorDao doctorDao;
    @Mock
    private IllnessDao illnessDao;
    @InjectMocks
    private DoctorService doctorService;
    private Doctor doctor;
    private Long id;
    private String idNumber;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        doctorService = new DoctorService(doctorDao, illnessDao);
        Set<Illness> illnesses;

        idNumber = "1234567890qwer";
        id = 1l;
        doctor = new Doctor();
        doctor.setId(1l);
        doctor.setFirstName("first");
        doctor.setLastName("last");
        doctor.setMiddleName("middle");
        doctor.setPricePerConsultation(BigDecimal.valueOf(2));
        doctor.setIdentificationNumber("1234567890qwer");
        doctor.setPhoneNumber(1234567);
        LocalDate date = LocalDate.now();
        doctor.setDateOfBirth(date);
        doctor.setUpdateDate(date);
        doctor.setCreateDate(date);

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
        illnesses = Sets.newSet(firstIllness, secondIllness);
        doctor.setIllnesses(illnesses);
    }

    @Test
    public void get_setCorrectId_shouldBeReturnPatient() {
        when(doctorDao.existsById(id)).thenReturn(true);
        when(doctorDao.findById(id)).thenReturn(doctor);
        Assert.assertEquals(doctorService.findById(id), doctor);
    }

    @Test(expected = EntityIsNotExistException.class)
    public void get_setIncorrectId_shouldBeReturnException() {
        when(doctorDao.existsById(id)).thenReturn(false);
        Assert.assertEquals(doctorService.findById(id), doctor);
    }

    @Test
    public void isPatientExist_setCorrectIdentificationNumber_shouldBeTrue() {
        when(doctorDao.existByIdentificationNumber(idNumber)).thenReturn(true);
        Assert.assertTrue(doctorService.existByIdentificationNumber(idNumber));
    }

    @Test
    public void isPatientExist_setIncorrectIdentificationNumber_shouldBeFalse() {
        when(doctorDao.existByIdentificationNumber(idNumber)).thenReturn(false);
        Assert.assertFalse(doctorService.existByIdentificationNumber(idNumber));
    }

    @Test
    public void findByIdentificationNumber_setCorrectIdentificationNumber_shouldBeReturnPatient() {
        when(doctorDao.findByIdentificationNumber(idNumber)).thenReturn(doctor);
        Assert.assertNotNull(doctorService.findByIdentificationNumber(idNumber));
    }

    @Test(expected = NoResultException.class)
    public void findByIdentificationNumber_setIncorrectIdentificationNumber_shouldBeReturnException() {
        when(doctorDao.findByIdentificationNumber(idNumber)).thenThrow(NoResultException.class);
        Assert.assertEquals(doctorService.findByIdentificationNumber(idNumber), doctor);
    }
}
