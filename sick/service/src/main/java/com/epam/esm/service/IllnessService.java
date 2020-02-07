package com.epam.esm.service;

import com.epam.esm.dao.IllnessDao;
import com.epam.esm.entity.Illness;
import com.epam.esm.exception.EntityIsAlreadyExistException;
import com.epam.esm.exception.EntityIsNotExistException;
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
        if (illnessDao.existsById(id)) {
            return illnessDao.findById(id);
        } else {
            throw new EntityIsNotExistException("illness is not exist");
        }
    }

    public List<Illness> findAll(int page, int size) {
        return illnessDao.findAll(page, size);
    }

    @Transactional
    public void create(Illness illness) {
        if (illnessDao.existsByName(illness.getName())) {
            throw new EntityIsAlreadyExistException("illness already exist");
        } else {
            illnessDao.create(illness);
        }
    }

    @Transactional
    public void update(Illness illness) {
        if (!illnessDao.existsById(illness.getId())) {
            throw new EntityIsNotExistException("illness is not exist");
        }
        if (illnessDao.isAnyIllnessExistWithName(illness.getId(), illness.getName())) {
            throw new EntityIsAlreadyExistException(
                    "illness with this name:" + illness.getName() + " already exist");
        }
        illnessDao.update(illness);
    }

    @Transactional
    public void delete(Long id) {
        if (illnessDao.existsById(id)) {
            illnessDao.delete(id);
        } else {
            throw new EntityIsNotExistException("illness is not exist");
        }
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
