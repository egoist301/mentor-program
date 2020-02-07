package com.epam.esm.converter;

import com.epam.esm.dto.order.OrderRequestDto;
import com.epam.esm.dto.order.OrderResponseDto;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Order;

import java.util.stream.Collectors;

public class OrderDtoConverter {
    public static OrderResponseDto convertToDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setCreateDate(order.getCreateDate());
        orderResponseDto
                .setDoctorResponseDtos(order.getDoctors().stream().map(DoctorDtoConverter::convertToDto).collect(
                        Collectors.toSet()));
        return orderResponseDto;
    }

    public static Order convertToEntity(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setDoctors(orderRequestDto.getDoctorRequestDtos().stream().map(temp -> {
            Doctor doctor = new Doctor();
            doctor.setId(temp);
            return doctor;
        }).collect(Collectors.toSet()));
        return order;
    }
}
