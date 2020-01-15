package com.epam.esm.service;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.PatientDao;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void create(Patient patient) {
        patientDao.create(patient);
        List<Illness> illnesses = patient.getIllnesses();
        infect(patient, illnesses);
    }

    public void update(Patient entity) {
        patientDao.update(entity);
        List<Illness> newIllnesses = entity.getIllnesses();
        List<Illness> oldIllnesses = illnessDao.findByPatientId(entity.getId());
        List<Illness> temp = new ArrayList<>(entity.getIllnesses());
        newIllnesses.removeAll(oldIllnesses);
        oldIllnesses.removeAll(temp);
        infect(entity, newIllnesses);
        treat(entity, oldIllnesses);
    }

    private void infect(Patient entity, List<Illness> newIllnesses) {
        if (!newIllnesses.isEmpty()) {
            newIllnesses.forEach(illness -> {
                if (illnessDao.isIllnessExistByName(illness.getName())) {
                    illness.setId(illnessDao.getID(illness.getName()));
                } else {
                    illnessDao.create(illness);
                }
                patientDao.saveRefPatientIllness(entity.getId(), illness.getId());
            });
        }
    }

    private void treat(Patient entity, List<Illness> oldIllnesses) {
        if (!oldIllnesses.isEmpty()) {
            oldIllnesses.forEach(illness -> {patientDao.removeRefPatientIllness(entity.getId(), illness.getId());});
        }
    }

    public void delete(Long id) {
        patientDao.delete(id);
    }

    public List<Patient> getAll(String searchByFirstName, String searchByLastName, String searchByMiddleName,
                               String searchByIllnessName, String searchByIllnessLatin, String sortBy, String order) {
        List<Patient> patients = patientDao
                .getAll(searchByFirstName, searchByLastName, searchByMiddleName, searchByIllnessName,
                        searchByIllnessLatin, sortBy, order);
        patients.forEach(patient -> {patient.setIllnesses(illnessDao.findByPatientId(patient.getId()));});
        return patients;
    }

    public void partialUpdate(Patient patient) {
        patientDao.partialUpdate(patient);
    }
}
