package com.epam.esm.facade;

import com.epam.esm.converter.OrderDtoConverter;
import com.epam.esm.dto.order.OrderRequestDto;
import com.epam.esm.dto.order.OrderResponseDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFacade {
    private OrderService orderService;
    private UserFacade userFacade;
    private UserService userService;

    @Autowired
    public OrderFacade(OrderService orderService, UserFacade userFacade, UserService userService) {
        this.orderService = orderService;
        this.userFacade = userFacade;
        this.userService = userService;
    }

    public OrderResponseDto get(Long id) {
        User user = userFacade.getCurrentUser();
        if (user.getRole().equals(Role.ROLE_USER)) {
            return OrderDtoConverter.convertToDto(orderService.findByIdForCurrentUser(id, user.getId()));
        }
        return OrderDtoConverter.convertToDto(orderService.findById(id));

    }

    public List<OrderResponseDto> getAll(int page, int size) {
        User user = userFacade.getCurrentUser();
        if (user.getRole().equals(Role.ROLE_USER)) {
            return orderService.findAllForCurrentUser(user.getId(), page, size).stream()
                    .map(OrderDtoConverter::convertToDto).collect(Collectors.toList());
        }
        return orderService.findAll(page, size).stream()
                .map(OrderDtoConverter::convertToDto).collect(Collectors.toList());
    }

    public OrderResponseDto createForCurrentUser(OrderRequestDto orderRequestDto) {
        User user = userFacade.getCurrentUser();
        return create(orderRequestDto, user);
    }

    public OrderResponseDto createForUser(OrderRequestDto orderRequestDto, Long userId) {
        User user = userService.findById(userId);
        return create(orderRequestDto, user);
    }

    private OrderResponseDto create(OrderRequestDto orderRequestDto, User user) {
        Order order = OrderDtoConverter.convertToEntity(orderRequestDto);
        order.setUser(user);
        orderService.create(order);
        return null;
    }
}
