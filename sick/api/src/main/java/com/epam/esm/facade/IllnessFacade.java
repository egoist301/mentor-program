package com.epam.esm.facade;

import com.epam.esm.converter.IllnessDtoConverter;
import com.epam.esm.dto.illness.IllnessRequestDto;
import com.epam.esm.dto.illness.IllnessResponseDto;
import com.epam.esm.entity.Illness;
import com.epam.esm.exception.EntityIsAlreadyExistException;
import com.epam.esm.exception.EntityIsNotExistException;
import com.epam.esm.service.IllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IllnessFacade {
    private IllnessService illnessService;

    @Autowired
    public IllnessFacade(IllnessService illnessService) {
        this.illnessService = illnessService;
    }

    public IllnessResponseDto get(Long id) {
        if (illnessService.isIllnessExist(id)) {
            return IllnessDtoConverter.convertToDto(illnessService.findById(id));
        } else {
            throw new EntityIsNotExistException("illness is not exist");
        }
    }

    public List<IllnessResponseDto> getAll() {
        return illnessService.findAll().stream().map(IllnessDtoConverter::convertToDto).collect(Collectors.toList());
    }

    public IllnessResponseDto create(IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        if (illnessService.isIllnessExist(illness.getName())) {
            throw new EntityIsAlreadyExistException("illness already exist");
        } else {
            illnessService.create(illness);
            return IllnessDtoConverter.convertToDto(illnessService.findByName(illness.getName()));
        }
    }

    public IllnessResponseDto update(Long id, IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        illness.setId(id);
        if (!illnessService.isIllnessExist(illness.getId())) {
            throw new EntityIsNotExistException("illness is not exist");
        } else if (illnessService.isAnyIllnessExistWithName(illness.getId(), illness.getName())) {
            throw new EntityIsAlreadyExistException(
                    "illness with this name:" + illness.getName() + " already exist");
        } else {
            illnessService.update(illness);
            return IllnessDtoConverter.convertToDto(illnessService.findById(id));
        }
    }

    public void delete(Long id) {
        if (illnessService.isIllnessExist(id)) {
            illnessService.delete(id);
        } else {
            throw new EntityIsNotExistException("illness is not exist");
        }
    }
}
