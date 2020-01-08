package com.epam.esm.controller;

import com.epam.esm.controller.dto.PatientDto;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/sick/patient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatientDto patient(@PathVariable("id") Long id) {
        return convertToDto(patientService.get(id));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto createPatient(@RequestBody PatientDto patientDto) throws ParseException {
        Patient patient = convertToEntity(patientDto);
        patientService.create(patient);
        return convertToDto(patient);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void updatePatient(@RequestBody PatientDto patientDto) throws ParseException {
        Patient patient = convertToEntity(patientDto);
        patientService.update(patient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable("id") Long id) {
        patientService.delete(id);
    }

    private PatientDto convertToDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setMiddleName(patient.getMiddleName());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        patientDto.setDateOfBirth(patient.getDateOfBirth());
        return patientDto;
    }

    private Patient convertToEntity(PatientDto patientDto) throws ParseException {
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setFirstName(patientDto.getFirstName());
        patient.setMiddleName(patientDto.getMiddleName());
        patient.setLastName(patientDto.getLastName());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setDateOfBirth(patientDto.getDateOfBirthConverted());
        return patient;
    }
}
