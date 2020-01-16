package com.epam.esm.facade;

import com.epam.esm.controller.converter.IllnessDtoConverter;
import com.epam.esm.controller.dto.IllnessPartialRequestDto;
import com.epam.esm.controller.dto.IllnessRequestDto;
import com.epam.esm.controller.dto.IllnessResponseDto;
import com.epam.esm.exception.AnyIllnessExistWithSameNameException;
import com.epam.esm.exception.IllnessAlreadyExistException;
import com.epam.esm.exception.IllnessNotExistException;
import com.epam.esm.repository.entity.Illness;
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
        return IllnessDtoConverter.convertToDto(illnessService.get(id));
    }

    public List<IllnessResponseDto> getAll() {
        return illnessService.getAll().stream().map(IllnessDtoConverter::convertToDto).collect(Collectors.toList());
    }

    public IllnessResponseDto create(IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        if (illnessService.isIllnessExist(illness.getName())) {
            throw new IllnessAlreadyExistException();
        } else {
            illnessService.create(illness);
            return IllnessDtoConverter.convertToDto(illnessService.findByName(illness.getName()).get(0));
        }
    }

    public IllnessResponseDto update(Long id, IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        illness.setId(id);
        if (!illnessService.isIllnessExist(illness.getId())) {
            throw new IllnessNotExistException();
        } else if (illnessService.isAnyIllnessExistWithName(illness.getId(), illness.getName())) {
            throw new AnyIllnessExistWithSameNameException();
        } else {
            illnessService.update(illness);
            return IllnessDtoConverter.convertToDto(illnessService.get(id));
        }
    }

    public void delete(Long id) {
        illnessService.delete(id);
    }

    public IllnessResponseDto partialUpdate(Long id, IllnessPartialRequestDto illnessPartialRequestDto) {
        Illness illness = IllnessDtoConverter.partialConvertToEntity(illnessPartialRequestDto);
        illness.setId(id);
        if (!illnessService.isIllnessExist(illness.getId())) {
            throw new IllnessNotExistException();
        } else if (illness.getName() != null &&
                illnessService.isAnyIllnessExistWithName(illness.getId(), illness.getName())) {
            throw new AnyIllnessExistWithSameNameException();
        } else {
            illnessService.partialUpdate(illness);
            return IllnessDtoConverter.convertToDto(illnessService.get(id));
        }
    }
}
