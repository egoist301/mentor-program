package com.epam.esm.controller;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sick/illness")
public class IllnessController {
    private final IllnessDao illnessDao;

    @Autowired
    public IllnessController(final IllnessDao illnessDao) {
        this.illnessDao = illnessDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Illness> patient(@PathVariable("id") Long id) {
        Illness illness = illnessDao.get(id);
        return new ResponseEntity<>(illness, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Illness> createPatient(@RequestBody Illness illness) {
        illnessDao.create(illness);
        return new ResponseEntity<>(illness, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Illness> deletePatient(@PathVariable("id") Long id) {
        illnessDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
