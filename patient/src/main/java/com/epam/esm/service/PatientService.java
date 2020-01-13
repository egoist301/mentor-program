package com.epam.esm.service;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.PatientDao;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientDao patientDao;
    private IllnessDao illnessDao;

    @Autowired
    public PatientService(PatientDao patientDao, IllnessDao illnessDao) {
        this.patientDao = patientDao;
        this.illnessDao = illnessDao;
    }

    public Patient get(Long id) {
        Patient patient = patientDao.get(id);
        patient.setIllnesses(illnessDao.findByPatientId(id));
        return patient;
    }

    public void create(Patient entity) {
        List<Illness> illnesses = entity.getIllnesses();
        for (Illness illness : illnesses) {
            illnessDao.create(illness);
        }
        patientDao.create(entity);
    }

    public void update(Patient entity) {
        patientDao.update(entity);
    }

    public void delete(Long id) {
        patientDao.delete(id);
    }

    public List<Patient> get(String firstName, String lastName, String middleName) {
        return patientDao.get(firstName, lastName, middleName);
    }
}
