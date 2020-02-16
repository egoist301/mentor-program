package com.epam.esm.facade;

import com.epam.esm.converter.OrderDtoConverter;
import com.epam.esm.converter.UserDtoConverter;
import com.epam.esm.dto.order.OrderRequestDto;
import com.epam.esm.dto.order.OrderResponseDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.security.UserPrincipal;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFacade {
    private OrderService orderService;
    private UserFacade userFacade;

    @Autowired
    public OrderFacade(OrderService orderService, UserFacade userFacade) {
        this.orderService = orderService;
        this.userFacade = userFacade;
    }

    public OrderResponseDto get(Long id) {
        return OrderDtoConverter.convertToDto(orderService.findById(id));
    }

    public List<OrderResponseDto> getAll(int page, int size) {
        User user = userFacade.getCurrentUser();
        return orderService.findAll(user.getId(), page, size).stream()
                .map(OrderDtoConverter::convertToDto).collect(Collectors.toList());
    }

    public OrderResponseDto create(OrderRequestDto orderRequestDto) {
        User user = userFacade.getCurrentUser();
        Order order = OrderDtoConverter.convertToEntity(orderRequestDto);
        order.setUser(user);
        orderService.create(order);
        return null;
    }
}
