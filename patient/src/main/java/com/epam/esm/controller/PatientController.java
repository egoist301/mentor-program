package com.epam.esm.controller;

import com.epam.esm.controller.converter.PatientDtoConverter;
import com.epam.esm.controller.dto.PatientDto;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.controller.converter.PatientDtoConverter.convertToDto;
import static com.epam.esm.controller.converter.PatientDtoConverter.convertToEntity;

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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto createPatient(@RequestBody PatientDto patientDto) throws ParseException {
        Patient patient = convertToEntity(patientDto);
        patientService.create(patient);
        return convertToDto(patient);
    }

    @PutMapping()
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

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<PatientDto> search(@RequestParam(value = "first", required = false, defaultValue = "") String firstName,
                                   @RequestParam(value = "last", required = false, defaultValue = "") String lastName,
                                   @RequestParam(value = "middle", required = false, defaultValue = "")
                                           String middleName) {
        return patientService
                .search(firstName, lastName, middleName).stream()
                .map(PatientDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
