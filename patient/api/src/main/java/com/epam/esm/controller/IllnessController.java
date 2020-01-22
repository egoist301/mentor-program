package com.epam.esm.controller;

import com.epam.esm.dto.IllnessPartialRequestDto;
import com.epam.esm.dto.IllnessRequestDto;
import com.epam.esm.dto.IllnessResponseDto;
import com.epam.esm.facade.IllnessFacade;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/illnesses")
public class IllnessController {
    private final IllnessFacade illnessFacade;

    @Autowired
    public IllnessController(IllnessFacade illnessFacade) {
        this.illnessFacade = illnessFacade;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessResponseDto get(@PathVariable("id") Long id) {
        Validator.validateId(id);
        return illnessFacade.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IllnessResponseDto> getAll() {
        return illnessFacade.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IllnessResponseDto create(@RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        return illnessFacade.create(illnessRequestDto);
    }

    //mentor requirement
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessResponseDto update(@PathVariable("id") Long id,
                                     @RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        Validator.validateId(id);
        return illnessFacade.update(id, illnessRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Validator.validateId(id);
        illnessFacade.delete(id);
    }

    //mentor requirement
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessResponseDto partialUpdate(@PathVariable("id") Long id, @RequestBody
            IllnessPartialRequestDto illnessPartialRequestDto) {
        return illnessFacade.partialUpdate(id, illnessPartialRequestDto);
    }


}
