package com.epam.esm.controller;

import com.epam.esm.dto.PatientPartialRequestDto;
import com.epam.esm.dto.PatientRequestDto;
import com.epam.esm.dto.PatientResponseDto;
import com.epam.esm.facade.PatientFacade;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/patients",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PatientController {
    private final PatientFacade patientFacade;

    @Autowired
    public PatientController(PatientFacade patientFacade) {
        this.patientFacade = patientFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> get(@PathVariable("id") Long id) {
        Validator.validateId(id);
        return new ResponseEntity<>(patientFacade.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAll(
            @RequestParam(value = "first_name", required = false) String searchByFirstName,
            @RequestParam(value = "last_name", required = false) String searchByLastName,
            @RequestParam(value = "middle_name", required = false) String searchByMiddleName,
            @RequestParam(value = "illness", required = false) String searchByIllnessName,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order) {
        Validator.validateSortAndOrder(sortBy, order);
        return new ResponseEntity<>(patientFacade
                .getAll(searchByFirstName, searchByLastName, searchByMiddleName, searchByIllnessName, sortBy, order),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> create(@RequestBody @Valid PatientRequestDto patientRequestDto) {
        PatientResponseDto patientResponseDto = patientFacade.create(patientRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(patientResponseDto.getId()).toUri());
        return new ResponseEntity<>(patientResponseDto, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> update(@PathVariable("id") Long id,
                                                     @RequestBody @Valid PatientRequestDto patientRequestDto) {
        Validator.validateId(id);
        return new ResponseEntity<>(patientFacade.update(id, patientRequestDto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDto> partialUpdate(@PathVariable("id") Long id,
                                                            @RequestBody
                                                            @Valid PatientPartialRequestDto patientPartialRequestDto) {
        Validator.validateId(id);
        return new ResponseEntity<>(patientFacade.partialUpdate(id, patientPartialRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Validator.validateId(id);
        patientFacade.delete(id);
    }
}
