package com.epam.esm.controller;

import com.epam.esm.dto.IllnessRequestDto;
import com.epam.esm.dto.IllnessResponseDto;
import com.epam.esm.service.IllnessService;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/illnesses")
public class IllnessController {
    private IllnessService illnessService;

    @Autowired
    public IllnessController(IllnessService illnessService) {
        this.illnessService = illnessService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IllnessResponseDto> get(@PathVariable("id") Long id) {
        Validator.validateId(id);
        return new ResponseEntity<>(illnessService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IllnessResponseDto> create(@RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        return new ResponseEntity<>(illnessService.create(illnessRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IllnessResponseDto>> getAll() {
        return new ResponseEntity<>(illnessService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IllnessResponseDto> update(@PathVariable Long id,
                                                     @RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        return new ResponseEntity<>(illnessService.update(id, illnessRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Validator.validateId(id);
        illnessService.delete(id);
    }
}
