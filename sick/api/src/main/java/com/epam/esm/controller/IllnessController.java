package com.epam.esm.controller;

import com.epam.esm.dto.illness.IllnessPartialRequestDto;
import com.epam.esm.dto.illness.IllnessRequestDto;
import com.epam.esm.dto.illness.IllnessResponseDto;
import com.epam.esm.facade.IllnessFacade;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/{id}")
    public ResponseEntity<IllnessResponseDto> get(@PathVariable("id") Long id) {
        Validator.validateId(id);
        return new ResponseEntity<>(illnessFacade.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<IllnessResponseDto>> getAll() {
        return new ResponseEntity<>(illnessFacade.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IllnessResponseDto> create(@RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        IllnessResponseDto illnessResponseDto = illnessFacade.create(illnessRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(illnessResponseDto.getId()).toUri());
        return new ResponseEntity<>(illnessResponseDto, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IllnessResponseDto> update(@PathVariable Long id,
                                                     @RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        return new ResponseEntity<>(illnessFacade.update(id, illnessRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Validator.validateId(id);
        illnessFacade.delete(id);
    }

   /* @PatchMapping("/{id}")
    public ResponseEntity<IllnessResponseDto> partialUpdate(@PathVariable Long id, @RequestBody @Valid
            IllnessPartialRequestDto illnessPartialRequestDto) {
        Validator.validateId(id);
        return new ResponseEntity<>(illnessFacade.partialUpdate(id, illnessPartialRequestDto), HttpStatus.OK);
    }*/
}
