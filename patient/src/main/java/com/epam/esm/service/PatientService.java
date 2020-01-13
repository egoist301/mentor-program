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
        patientDao.create(entity);
        List<Illness> illnesses = entity.getIllnesses();
        if (!illnesses.isEmpty()) {
            illnesses.forEach(illness -> {
                if (illnessDao.isIllnessExistByName(illness.getName())) {
                    illness.setId(illnessDao.getID(illness.getName()));
                } else {
                    illnessDao.create(illness);
                }
                patientDao.saveIllness(entity.getId(), illness.getId());
            });
        }
    }

    public void update(Patient entity) {
        patientDao.update(entity);
    }

    public void delete(Long id) {
        patientDao.delete(id);
    }

    public List<Patient> getAll(String firstName, String lastName, String middleName) {
        List<Patient> patients = patientDao.getAll(firstName, lastName, middleName);
        patients.forEach(patient -> {patient.setIllnesses(illnessDao.findByPatientId(patient.getId()));});
        return patients;
    }
}
