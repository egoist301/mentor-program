package com.epam.esm.controller;

import com.epam.esm.controller.converter.PatientDtoConverter;
import com.epam.esm.controller.dto.PatientDto;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
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
    public PatientDto get(@Valid @PathVariable("id") @Min(1) Long id) {
        return convertToDto(patientService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto createPatient(@RequestBody @Valid PatientDto patientDto) throws ParseException {
        Patient patient = convertToEntity(patientDto);
        patientService.create(patient);
        return convertToDto(patient);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePatient(@PathVariable("id") Long id, @RequestBody @Valid PatientDto patientDto)
            throws ParseException {
        patientDto.setId(id);
        Patient patient = convertToEntity(patientDto);
        patientService.update(patient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable("id") Long id) {
        patientService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PatientDto> getAll(
            @RequestParam(value = "first", required = false, defaultValue = "") String searchByFirstName,
            @RequestParam(value = "last", required = false, defaultValue = "") String searchByLastName,
            @RequestParam(value = "middle", required = false, defaultValue = "") String searchByMiddleName,
            @RequestParam(value = "name", required = false, defaultValue = "") String searchByIllnessName,
            @RequestParam(value = "latin", required = false, defaultValue = "") String searchByIllnessLatin,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order) {
        return patientService.getAll(searchByFirstName, searchByLastName, searchByMiddleName, searchByIllnessName,
                searchByIllnessLatin, sortBy, order).stream().map(PatientDtoConverter::convertToDto).collect(Collectors.toList());
    }
}
