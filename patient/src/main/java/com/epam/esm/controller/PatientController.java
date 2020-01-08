package com.epam.esm.controller;

import com.epam.esm.repository.entity.Patient;
import com.epam.esm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sick/patient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> patient(@PathVariable("id") Long id) {
        Patient patient = patientService.get(id);
        return new ResponseEntity<>(patient,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        patientService.create(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        patientService.update(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long id) {
        patientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
