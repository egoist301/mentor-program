package com.epam.esm.controller;

import com.epam.esm.repository.PatientDao;
import com.epam.esm.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sick/patient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PatientController {
    @Autowired
    private PatientDao patientDao;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Patient> patient(@PathVariable("id") Long id) {
        Patient patient = patientDao.get(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        patientDao.create(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping(value = "/")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        patientDao.update(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long id) {
        patientDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
