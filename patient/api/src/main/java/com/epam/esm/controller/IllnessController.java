package com.epam.esm.controller;

import com.epam.esm.controller.converter.IllnessDtoConverter;
import com.epam.esm.controller.dto.IllnessResponseDto;
import com.epam.esm.controller.dto.IllnessDtoPatch;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.service.IllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.controller.converter.IllnessDtoConverter.*;

@RestController
@RequestMapping("/sick/illness")
public class IllnessController {
    private final IllnessService illnessService;

    @Autowired
    public IllnessController(IllnessService illnessService) {
        this.illnessService = illnessService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessResponseDto get(@PathVariable("id") Long id) {
        return convertToDto(illnessService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IllnessResponseDto create(@RequestBody @Valid IllnessResponseDto illnessResponseDto) {
        Illness illness = convertToEntity(illnessResponseDto);
        illnessService.create(illness);
        return convertToDto(illness);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessResponseDto update(@PathVariable("id") Long id, @RequestBody @Valid IllnessResponseDto illnessResponseDto) {
        Illness illness = convertToEntity(illnessResponseDto);
        illnessService.update(illness);
        return convertToDto(illness);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        illnessService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IllnessResponseDto> getAll() {
        return illnessService.getAll().stream().map(IllnessDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessDtoPatch partialUpdate(@RequestBody IllnessDtoPatch illnessDto, @PathVariable("id") Long id) {
        illnessDto.setId(id);
        Illness illness = convertToEntityPatch(illnessDto);
        illnessService.partialUpdate(illness);
        return convertToDtoPatch(illness);
    }
}
