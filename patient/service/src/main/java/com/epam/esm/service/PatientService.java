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
        //List<Illness> newIllnesses = entity.getIllnesses();
        // List<Illness> oldIllnesses = illnessDao.findByPatientId(entity.getId());
//        List<Illness> temp = new ArrayList<>(entity.getIllnesses());
//        newIllnesses.removeAll(oldIllnesses);
//        oldIllnesses.removeAll(temp);
//        infect(entity, newIllnesses);
//        treat(entity, oldIllnesses);
    }

    public void saveRefPatientIlness(Long patientId, Long illnessId) {
        patientDao.saveRefPatientIllness(patientId, illnessId);
    }

    private void infect(Patient entity, List<Illness> newIllnesses) {
        if (!newIllnesses.isEmpty()) {
            newIllnesses.forEach(illness -> {
                if (illnessDao.isIllnessExistByName(illness.getName())) {
                    illness.setId(illnessDao.findByName(illness.getName()).getId());
                } else {
                    illnessDao.create(illness);
                }
                patientDao.saveRefPatientIllness(entity.getId(), illness.getId());
            });
        }
    }

    private void treat(Patient entity, List<Illness> oldIllnesses) {
        if (!oldIllnesses.isEmpty()) {
            oldIllnesses.forEach(illness -> {
                patientDao.removeRefPatientIllness(entity.getId(), illness.getId());
            });
        }
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

    public boolean isAnyPatientExistWithIdentificationNumber(Long id, String identificationNumber) {
        return !patientDao.findByIdentificationNumberWithDifferentId(id, identificationNumber).isEmpty();
    }

    public boolean isRefPatientIllnessExist(Long patientId, Long illnessId) {
        return patientDao.isRefPatientIllnessExist(patientId, illnessId) != null;
    }

    public void removeRefPatientIllness(Long patientId, Long illnessId) {
        patientDao.removeRefPatientIllness(patientId, illnessId);
    }
}
