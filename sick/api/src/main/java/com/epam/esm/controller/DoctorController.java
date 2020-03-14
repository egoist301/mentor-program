package com.epam.esm.controller;

import com.epam.esm.constant.AppConstants;
import com.epam.esm.dto.doctor.DoctorPartialRequestDto;
import com.epam.esm.dto.doctor.DoctorRequestDto;
import com.epam.esm.dto.doctor.DoctorResponseDto;
import com.epam.esm.facade.DoctorFacade;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
public class DoctorController {
    private DoctorFacade doctorFacade;

    @Autowired
    public DoctorController(DoctorFacade doctorFacade) {
        this.doctorFacade = doctorFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> get(@PathVariable Long id) {
        Validator.validateId(id);
        return new ResponseEntity<>(doctorFacade.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAll(
            @RequestParam(value = "first_name", required = false, defaultValue = "") String searchByFirstName,
            @RequestParam(value = "last_name", required = false, defaultValue = "") String searchByLastName,
            @RequestParam(value = "middle_name", required = false, defaultValue = "") String searchByMiddleName,
            @RequestParam(value = "illness", required = false) List<String> searchByIllnessName,
            @RequestParam(value = "sort", required = false, defaultValue = "dateOfBirth") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        Validator.validateSortAndOrder(sortBy, order);
        Validator.validatePageNumberAndSize(page, size);
        return new ResponseEntity<>(doctorFacade
                .getAll(Arrays.asList(searchByFirstName, searchByLastName, searchByMiddleName),
                        (Objects.nonNull(searchByIllnessName) ? searchByIllnessName : Collections.emptyList()),
                        sortBy, order, page, size), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DoctorResponseDto> create(@RequestBody @Valid DoctorRequestDto doctorRequestDto) {
        DoctorResponseDto doctorResponseDto = doctorFacade.create(doctorRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(doctorResponseDto.getId()).toUri());
        return new ResponseEntity<>(doctorResponseDto, httpHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> update(@PathVariable("id") Long id,
                                                    @RequestBody @Valid DoctorRequestDto doctorRequestDto) {
        Validator.validateId(id);
        return new ResponseEntity<>(doctorFacade.update(id, doctorRequestDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> partialUpdate(@PathVariable("id") Long id, @RequestBody
    @Valid DoctorPartialRequestDto doctorPartialRequestDto) {
        Validator.validateId(id);
        return new ResponseEntity<>(doctorFacade.partialUpdate(id, doctorPartialRequestDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Validator.validateId(id);
        doctorFacade.delete(id);
    }
}
