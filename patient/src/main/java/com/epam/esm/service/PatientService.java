package com.epam.esm.service;

import com.epam.esm.repository.PatientDao;
import com.epam.esm.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientDao patientDao;

    @Autowired
    public PatientService(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public Patient get(Long id) {
        return patientDao.get(id);
    }

    public void create(Patient entity) {
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
