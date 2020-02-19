package com.epam.esm.controller;

import com.epam.esm.constant.AppConstants;
import com.epam.esm.dto.illness.IllnessRequestDto;
import com.epam.esm.dto.illness.IllnessResponseDto;
import com.epam.esm.facade.IllnessFacade;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/illnesses")
public class IllnessController {
    private IllnessFacade illnessFacade;

    @Autowired
    public IllnessController(IllnessFacade illnessFacade) {
        this.illnessFacade = illnessFacade;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<IllnessResponseDto> get(@PathVariable("id") Long id) {
        Validator.validateId(id);
        return new ResponseEntity<>(illnessFacade.get(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<IllnessResponseDto>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        Validator.validatePageNumberAndSize(page, size);
        return new ResponseEntity<>(illnessFacade.getAll(page, size), HttpStatus.OK);
    }

    @PermitAll
    @GetMapping("/get_widely_used")
    public ResponseEntity<IllnessResponseDto> getWidelyUsed() {
        return new ResponseEntity<>(illnessFacade.getWidelyUsed(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<IllnessResponseDto> create(@RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        IllnessResponseDto illnessResponseDto = illnessFacade.create(illnessRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(illnessResponseDto.getId()).toUri());
        return new ResponseEntity<>(illnessResponseDto, httpHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<IllnessResponseDto> update(@PathVariable Long id,
                                                     @RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        Validator.validateId(id);
        return new ResponseEntity<>(illnessFacade.update(id, illnessRequestDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Validator.validateId(id);
        illnessFacade.delete(id);
    }
}
