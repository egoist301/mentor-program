package com.epam.esm.service;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IllnessService {
    private IllnessDao illnessDao;

    @Autowired
    public IllnessService(IllnessDao illnessDao) {
        this.illnessDao = illnessDao;
    }

    public Illness get(Long id) {
        return illnessDao.get(id);
    }

    public void create(Illness entity) {
        if (!illnessDao.isIllnessExistByName(entity.getName())) {
            illnessDao.create(entity);
        }
        else {
            throw new IllnessExistException("Illness already exist.");
        }
    }

    public void delete(Long id) {
        illnessDao.delete(id);
    }

    public List<Illness> getAll() {
        return illnessDao.getAll();
    }
}
