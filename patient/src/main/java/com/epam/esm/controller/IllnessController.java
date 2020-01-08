package com.epam.esm.controller;

import com.epam.esm.repository.entity.Illness;
import com.epam.esm.service.IllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Illness> patient(@PathVariable("id") Long id) {
        Illness illness = illnessService.get(id);
        return new ResponseEntity<>(illness, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Illness> createPatient(@RequestBody Illness illness) {
        illnessService.create(illness);
        return new ResponseEntity<>(illness, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Illness> deletePatient(@PathVariable("id") Long id) {
        illnessService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
