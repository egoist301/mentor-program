package com.epam.esm.controller;

import com.epam.esm.repository.PatientDao;
import com.epam.esm.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sick/patient/")
public class PatientController {
    @Autowired
    private PatientDao patientDao;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Patient> patient(@PathVariable("id") Long id) {
        Patient patient = patientDao.get(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        patientDao.create(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        patientDao.update(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long id) {
        patientDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
