package com.epam.esm.service;

import com.epam.esm.converter.IllnessDtoConverter;
import com.epam.esm.dao.IllnessDao;
import com.epam.esm.dto.illness.IllnessPartialRequestDto;
import com.epam.esm.dto.illness.IllnessRequestDto;
import com.epam.esm.dto.illness.IllnessResponseDto;
import com.epam.esm.entity.Illness;
import com.epam.esm.exception.EntityIsAlreadyExistException;
import com.epam.esm.exception.EntityIsNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IllnessService {
    private final IllnessDao illnessDao;

    @Autowired
    public IllnessService(IllnessDao illnessDao) {
        this.illnessDao = illnessDao;
    }

    public IllnessResponseDto getById(Long id) {
        isIllnessNotExist(id);
        return IllnessDtoConverter.convertToDto(illnessDao.findById(id));
    }

    public List<IllnessResponseDto> getAll() {
        return illnessDao.findAll().stream().map(IllnessDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public IllnessResponseDto create(IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);

        isIllnessExist(illness.getName());
        illnessDao.create(illness);
        return IllnessDtoConverter.convertToDto(illnessDao.findByName(illness.getName()));
    }

    @Transactional
    public IllnessResponseDto update(Long id, IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        illness.setId(id);

        isIllnessNotExist(illness.getId());
        isAnyIllnessExistWithName(illness.getId(), illness.getName());

        illnessDao.update(illness);
        return IllnessDtoConverter.convertToDto(illnessDao.findById(id));
    }

    @Transactional//TODO
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
    }

    @Transactional
    public void delete(Long id) {
        isIllnessNotExist(id);
        illnessDao.delete(id);
    }

    private void isAnyIllnessExistWithName(Long id, String name) {
        if (!illnessDao.findByNameWithDifferentId(id, name).isEmpty()) {
            throw new EntityIsAlreadyExistException("illness with this name:" + name + " already exist");
        }
    }

    private void isIllnessNotExist(Long id) {
        if (illnessDao.existsById(id).isEmpty()) {
            throw new EntityIsNotExistException("illness is not exist");
        }
    }

    private void isIllnessExist(String name) {
        if (!illnessDao.existsByName(name).isEmpty()) {
            throw new EntityIsAlreadyExistException("illness already exist");
        }
    }
}
