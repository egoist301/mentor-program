package com.epam.esm.controller;

import com.epam.esm.dto.PatientResponseDto;
import com.epam.esm.facade.RandomFacade;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random")
public class RandomController {
    private RandomFacade randomFacade;

    @Autowired
    public RandomController(RandomFacade randomFacade) {
        this.randomFacade = randomFacade;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updateIllnesses(@PathVariable("id") Long id) {
        Validator.validateId(id);
        return new ResponseEntity<>(randomFacade.updateIllnesses(id), HttpStatus.OK);
    }
}
