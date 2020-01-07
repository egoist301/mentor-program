package com.epam.esm.controller;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sick/illness/")
public class IllnessController {
    @Autowired
    private IllnessDao illnessDao;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Illness> patient(@PathVariable("id") Long id) {
        Illness illness = illnessDao.get(id);
        return new ResponseEntity<>(illness, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Illness> createPatient(@RequestBody Illness illness) {
        illnessDao.create(illness);
        return new ResponseEntity<>(illness, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Illness> deletePatient(@PathVariable("id") Long id) {
        illnessDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
