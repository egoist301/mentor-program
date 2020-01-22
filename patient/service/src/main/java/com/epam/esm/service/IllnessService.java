package com.epam.esm.service;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class IllnessService {
    private IllnessDao illnessDao;

    @Autowired
    public IllnessService(IllnessDao illnessDao) {
        this.illnessDao = illnessDao;
    }

    public Illness get(Long id) {
        return illnessDao.findById(id).get(0);
    }

    public List<Illness> getAll() {
        return illnessDao.getAll();
    }

    public void create(Illness illness) {
        illnessDao.create(illness);
    }

    public void delete(Long id) {
        illnessDao.delete(id);
    }

    public void update(Illness illness) {
        illnessDao.update(illness);
    }

    public void partialUpdate(Illness illness) {
        illnessDao.partialUpdate(illness);
    }

    public Illness findByName(String name) {
        return illnessDao.findByName(name).get(0);
    }

    public boolean isIllnessExist(String name) {
        return !illnessDao.findByName(name).isEmpty();
    }

    public Set<Illness> findByPatientId(Long patientId) {
        return illnessDao.findByPatientId(patientId);
    }

    public boolean isIllnessExist(Long id) {
        return !illnessDao.findById(id).isEmpty();
    }

    public boolean isAnyIllnessExistWithName(Long id, String name) {
        return !illnessDao.findByNameWithDifferentId(name, id).isEmpty();
    }
}
