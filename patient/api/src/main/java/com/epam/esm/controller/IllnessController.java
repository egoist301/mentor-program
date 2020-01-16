package com.epam.esm.controller;

import com.epam.esm.controller.dto.IllnessPartialRequestDto;
import com.epam.esm.controller.dto.IllnessRequestDto;
import com.epam.esm.controller.dto.IllnessResponseDto;
import com.epam.esm.facade.IllnessFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/illness")
public class IllnessController {
    private final IllnessFacade illnessFacade;

    @Autowired
    public IllnessController(IllnessFacade illnessFacade) {
        this.illnessFacade = illnessFacade;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessResponseDto get(@PathVariable("id") Long id) {
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessResponseDto update(@PathVariable("id") Long id,
                                     @RequestBody @Valid IllnessRequestDto illnessRequestDto) {
        return illnessFacade.update(id, illnessRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        illnessFacade.delete(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IllnessResponseDto partialUpdate(@PathVariable("id") Long id, @RequestBody
            IllnessPartialRequestDto illnessPartialRequestDto) {
        return illnessFacade.partialUpdate(id, illnessPartialRequestDto);
    }
}
