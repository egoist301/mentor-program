package com.epam.esm.controller;

import com.epam.esm.repository.PatientDao;
import com.epam.esm.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sick/patient")
public class PatientController {
    private final PatientDao patientDao;

    @Autowired
    public PatientController(final PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> patient(@PathVariable("id") Long id) {
        Patient patient = patientDao.get(id);
        return new ResponseEntity<>(patient,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        patientDao.create(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        patientDao.update(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long id) {
        patientDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
