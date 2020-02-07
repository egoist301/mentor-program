package com.epam.esm.facade;

import com.epam.esm.converter.OrderDtoConverter;
import com.epam.esm.dto.order.OrderRequestDto;
import com.epam.esm.dto.order.OrderResponseDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityIsNotExistException;
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

    @Autowired
    public OrderFacade(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderResponseDto get(Long id) {
        if (orderService.isOrderExist(id)) {
            return OrderDtoConverter.convertToDto(orderService.findById(id));
        } else {
            throw new EntityIsNotExistException("order is not exist");
        }
    }

    public List<OrderResponseDto> getAll() {
        UserPrincipal userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.findAll(convertToUser(userPrincipal).getId()).stream().map(OrderDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderResponseDto create(OrderRequestDto orderRequestDto) {
        UserPrincipal userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = convertToUser(userPrincipal);
        Order order = OrderDtoConverter.convertToEntity(orderRequestDto);
        order.setUser(user);
        orderService.fillDoctors(order);
        orderService.create(order);
        return null;
    }

    /*public OrderResponseDto update(Long id, OrderRequestDto orderRequestDto) {
        if (orderService.isOrderExist(id)) {
            UserPrincipal principal =
                    (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = convertToUser(principal);
            Order order = OrderDtoConverter.convertToEntity(orderRequestDto);
            order.setId(id);
            order.setUser(user);
            orderService.fillDoctors(order);
            orderService.update(order);
            return OrderDtoConverter.convertToDto(orderService.findById(id));
        } else {
            throw new EntityIsNotExistException("order is not exist");
        }
    }

    public void delete(Long id) {
        if (orderService.isOrderExist(id)) {
            orderService.delete(id);
        } else {
            throw new EntityIsNotExistException("order is not exist");
        }
    }*/

    private User convertToUser(UserPrincipal userPrincipal) {
        User user = new User();
        user.setId(userPrincipal.getId());
        user.setUsername(userPrincipal.getUsername());
        user.setPassword(userPrincipal.getPassword());
        user.setRole(user.getRole());
        return user;
    }
}
