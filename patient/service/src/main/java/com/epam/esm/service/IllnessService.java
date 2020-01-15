package com.epam.esm.service;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    public void create(Illness illness) {
        try {
            illnessDao.create(illness);
        } catch (DuplicateKeyException ex) {
            throw new IllnessNameExistException("name already exist");
        }
    }

    public void delete(Long id) {
        illnessDao.delete(id);
    }

    public List<Illness> getAll() {
        return illnessDao.getAll();
    }

    public void update(Illness illness) {
        try {
            illnessDao.update(illness);
        } catch (DuplicateKeyException ex) {
            throw new IllnessNameExistException("name already exist");
        }
    }

    public void partialUpdate(Illness illness) {
        try {
            illnessDao.partialUpdate(illness);
        } catch (DuplicateKeyException ex) {
            throw new IllnessNameExistException("name already exist");
        }
    }
}
