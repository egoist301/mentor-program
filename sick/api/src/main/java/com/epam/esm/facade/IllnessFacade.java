package com.epam.esm.facade;

import com.epam.esm.converter.IllnessDtoConverter;
import com.epam.esm.dto.illness.IllnessRequestDto;
import com.epam.esm.dto.illness.IllnessResponseDto;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Illness;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.IllnessService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IllnessFacade {
    private IllnessService illnessService;

    @Autowired
    public IllnessFacade(IllnessService illnessService) {
        this.illnessService = illnessService;
    }

    public IllnessResponseDto get(Long id) {
        return IllnessDtoConverter.convertToDto(illnessService.findById(id));
    }

    public List<IllnessResponseDto> getAll(int page, int size) {
        return illnessService.findAll(page, size).stream().map(IllnessDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public IllnessResponseDto create(IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        illnessService.create(illness);
        return IllnessDtoConverter.convertToDto(illnessService.findByName(illness.getName()));
    }

    public IllnessResponseDto update(Long id, IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        illness.setId(id);
        illnessService.update(illness);
        return IllnessDtoConverter.convertToDto(illnessService.findById(id));
    }

    public void delete(Long id) {
        illnessService.delete(id);
    }

    public IllnessResponseDto getWidelyUsed() {
        return IllnessDtoConverter.convertToDto(illnessService.findWidelyUsed());
    }
}
