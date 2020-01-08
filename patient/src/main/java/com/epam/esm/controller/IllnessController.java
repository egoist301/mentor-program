package com.epam.esm.controller;

import com.epam.esm.controller.dto.IllnessDto;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.service.IllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public IllnessDto patient(@PathVariable("id") Long id) {
        return convertToDto(illnessService.get(id));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public IllnessDto createPatient(@RequestBody IllnessDto illnessDto) {
        Illness illness = convertToEntity(illnessDto);
        illnessService.create(illness);
        return convertToDto(illness);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable("id") Long id) {
        illnessService.delete(id);
    }

    private IllnessDto convertToDto(Illness illness) {
        IllnessDto illnessDto = new IllnessDto();
        illnessDto.setId(illness.getId());
        illnessDto.setChanceToDie(illness.getChanceToDie());
        illnessDto.setName(illness.getName());
        illnessDto.setNameInLatin(illness.getNameInLatin());
        return illnessDto;
    }

    private Illness convertToEntity(IllnessDto illnessDto) {
        Illness illness = new Illness();
        illness.setName(illnessDto.getName());
        illness.setNameInLatin(illnessDto.getNameInLatin());
        illness.setChanceToDie(illnessDto.getChanceToDie());
        illness.setId(illnessDto.getId());
        return illness;
    }
}
