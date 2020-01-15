package com.epam.esm.controller;

import com.epam.esm.controller.converter.IllnessDtoConverter;
import com.epam.esm.controller.dto.IllnessDto;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.service.IllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.controller.converter.IllnessDtoConverter.convertToDto;
import static com.epam.esm.controller.converter.IllnessDtoConverter.convertToEntity;

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
    public IllnessDto get(@PathVariable("id") Long id) {
        return convertToDto(illnessService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IllnessDto create(@RequestBody @Valid IllnessDto illnessDto) {
        Illness illness = convertToEntity(illnessDto);
        illnessService.create(illness);
        return convertToDto(illness);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        illnessService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IllnessDto> getAll() {
        return illnessService.getAll().stream().map(IllnessDtoConverter::convertToDto).collect(Collectors.toList());
    }
}
