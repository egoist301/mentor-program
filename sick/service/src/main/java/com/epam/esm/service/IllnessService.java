package com.epam.esm.service;

import com.epam.esm.dao.IllnessDao;
import com.epam.esm.entity.Illness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IllnessService {
    private final IllnessDao illnessDao;

    @Autowired
    public IllnessService(IllnessDao illnessDao) {
        this.illnessDao = illnessDao;
    }

    public Illness findById(Long id) {
        return illnessDao.findById(id);
    }

    public List<Illness> findAll() {
        return illnessDao.findAll();
    }

    @Transactional
    public void create(Illness illness) {
        illnessDao.create(illness);
    }

    @Transactional
    public void update(Illness illness) {
        illnessDao.update(illness);
    }

    @Transactional
    public void delete(Long id) {
        illnessDao.delete(id);
    }

    public boolean isAnyIllnessExistWithName(Long id, String name) {
        return illnessDao.isAnyIllnessExistWithName(id, name);
    }

    public boolean isIllnessExist(Long id) {
        return illnessDao.existsById(id);
    }

    public boolean isIllnessExist(String name) {
        return illnessDao.existsByName(name);
    }

    public Illness findByName(String name) {
        return illnessDao.findByName(name);
    }
    /* @Transactional
     public IllnessResponseDto partialUpdate(Long id, IllnessPartialRequestDto illnessPartialRequestDto) {
         isIllnessNotExist(id);
         Illness illness = IllnessDtoConverter.partialConvertToEntity(illnessPartialRequestDto);
         illness.setId(id);
         if (illness.getName() != null) {
             isAnyIllnessExistWithName(illness.getId(), illness.getName());
         }
         Illness illnessFromDB = illnessDao.findById(id);
         partialUpdate(illnessFromDB);
         illnessDao.update(illnessFromDB);
         return IllnessDtoConverter.convertToDto(illnessFromDB);
     }

     private void partialUpdate(Illness illness) {
         String name = illness.getName();
         String description = illness.getDescription();
         Integer chance = illness.getChanceToDie();
         if (name != null) {
             illness.setName(name);
         }
         if (description != null) {
             illness.setDescription(description);
         }
         if (chance != null) {
             illness.setChanceToDie(chance);
         }
     }*/
}
