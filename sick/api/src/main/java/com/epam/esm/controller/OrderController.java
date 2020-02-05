package com.epam.esm.controller;

import com.epam.esm.dto.order.OrderRequestDto;
import com.epam.esm.dto.order.OrderResponseDto;
import com.epam.esm.service.OrderService;
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

@RestController
@RequestMapping("users/{userId}/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> get(@PathVariable Long id, @PathVariable Long userId) {
        Validator.validateId(id);
        Validator.validateId(userId);
        return new ResponseEntity<>(orderService.findById(id, userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@PathVariable Long userId,
                                                   @RequestBody @Valid OrderRequestDto orderRequestDto) {
        Validator.validateId(userId);
        orderService.create(orderRequestDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> update(@PathVariable Long id, @PathVariable Long userId,
                                                   @RequestBody @Valid OrderRequestDto orderRequestDto) {
        Validator.validateId(id);
        Validator.validateId(userId);
        orderService.update(id, userId, orderRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @PathVariable Long userId) {
        Validator.validateId(id);
        Validator.validateId(userId);
        orderService.delete(id, userId);
    }
}
