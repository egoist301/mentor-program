package com.epam.esm.service;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        illnessDao.create(entity);
    }

    public void delete(Long id) {
        illnessDao.delete(id);
    }
}
