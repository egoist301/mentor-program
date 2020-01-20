package com.epam.esm.controller;

import com.epam.esm.dto.PatientPartialRequestDto;
import com.epam.esm.dto.PatientRequestDto;
import com.epam.esm.dto.PatientResponseDto;
import com.epam.esm.exception.IncorrectPathVariableException;
import com.epam.esm.facade.PatientFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/patient",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PatientController {
    private final PatientFacade patientFacade;

    @Autowired
    public PatientController(PatientFacade patientFacade) {
        this.patientFacade = patientFacade;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatientResponseDto get(@PathVariable("id") Long id) {
        validateId(id);
        return patientFacade.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PatientResponseDto> getAll(
            @RequestParam(value = "first", required = false, defaultValue = "") String searchByFirstName,
            @RequestParam(value = "last", required = false, defaultValue = "") String searchByLastName,
            @RequestParam(value = "middle", required = false, defaultValue = "") String searchByMiddleName,
            @RequestParam(value = "name", required = false, defaultValue = "") String searchByIllnessName,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order) {
        validateSortAndOrder(sortBy, order);
        return patientFacade
                .getAll(searchByFirstName, searchByLastName, searchByMiddleName, searchByIllnessName, sortBy, order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDto create(@RequestBody @Valid PatientRequestDto patientRequestDto) {
        return patientFacade.create(patientRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatientResponseDto update(@PathVariable("id") Long id,
                                     @RequestBody @Valid PatientRequestDto patientRequestDto) {
        validateId(id);
        return patientFacade.update(id, patientRequestDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatientResponseDto partialUpdate(@PathVariable("id") Long id,
                                            @RequestBody @Valid PatientPartialRequestDto patientPartialRequestDto) {
        validateId(id);
        return patientFacade.partialUpdate(id, patientPartialRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        validateId(id);
        patientFacade.delete(id);
    }

    private void validateId(Long id) {
        if (id <= 0) {
            throw new IncorrectPathVariableException();
        }
    }

    private void validateSortAndOrder(String sortBy, String order) {
        if ((sortBy != null &&
                !(sortBy.equals("first_name") || sortBy.equals("last_name") || sortBy.equals("middle_name") ||
                        sortBy.equals("date_of_birth"))) ||
                (order != null && !(order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc")))) {
            throw new IncorrectPathVariableException();
        }
    }
}
