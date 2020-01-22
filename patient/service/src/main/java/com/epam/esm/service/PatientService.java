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
        return patientDao.findById(id);
    }

    public List<Patient> getAll(String searchByFirstName, String searchByLastName, String searchByMiddleName,
                                String searchByIllnessName, String sortBy, String order) {
        return patientDao
                .getAll(searchByFirstName, searchByLastName, searchByMiddleName, searchByIllnessName, sortBy, order);
    }

    public void create(Patient patient) {
        patientDao.create(patient);
    }

    public void update(Patient entity) {
        patientDao.update(entity);
    }

    public void saveRefPatientIllness(Long patientId, Long illnessId) {
        patientDao.saveRefPatientIllness(patientId, illnessId);
    }

    public void delete(Long id) {
        patientDao.delete(id);
    }


    public void partialUpdate(Patient patient) {
        patientDao.partialUpdate(patient);
    }

    public Patient findByIdentificationNumber(String identificationNumber) {
        return patientDao.findByIdentificationNumber(identificationNumber);
    }

    public boolean isPatientExist(String identificationNumber) {
        return patientDao.findByIdentificationNumber(identificationNumber) != null;
    }

    public boolean isPatientExist(Long id) {
        return patientDao.findById(id) != null;
    }

    public boolean isRefPatientIllnessExist(Long patientId, Long illnessId) {
        return patientDao.isRefPatientIllnessExist(patientId, illnessId) != null;
    }

    public void removeRefPatientIllness(Long patientId, Long illnessId) {
        patientDao.removeRefPatientIllness(patientId, illnessId);
    }
}
