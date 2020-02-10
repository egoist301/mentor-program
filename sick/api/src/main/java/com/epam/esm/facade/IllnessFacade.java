package com.epam.esm.facade;

import com.epam.esm.converter.IllnessDtoConverter;
import com.epam.esm.dto.illness.IllnessRequestDto;
import com.epam.esm.dto.illness.IllnessResponseDto;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Illness;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.IllnessService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IllnessFacade {
    private IllnessService illnessService;
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public IllnessFacade(IllnessService illnessService, UserService userService,
                         OrderService orderService) {
        this.illnessService = illnessService;
        this.userService = userService;
        this.orderService = orderService;
    }

    public IllnessResponseDto get(Long id) {
        return IllnessDtoConverter.convertToDto(illnessService.findById(id));
    }

    public List<IllnessResponseDto> getAll(int page, int size) {
        return illnessService.findAll(page, size).stream().map(IllnessDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public IllnessResponseDto create(IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        illnessService.create(illness);
        return IllnessDtoConverter.convertToDto(illnessService.findByName(illness.getName()));
    }

    public IllnessResponseDto update(Long id, IllnessRequestDto illnessRequestDto) {
        Illness illness = IllnessDtoConverter.convertToEntity(illnessRequestDto);
        illness.setId(id);
        illnessService.update(illness);
        return IllnessDtoConverter.convertToDto(illnessService.findById(id));
    }

    public void delete(Long id) {
        illnessService.delete(id);
    }

    public IllnessResponseDto getWidelyUsed() {
        int page = 1;
        Integer maxUserSize = userService.getCount();
        List<User> users = userService.findAll(page, maxUserSize);
        Map<User, List<Order>> userOrdersMap = getUserOrdersMap(users, page);
        Map<User, BigDecimal> userOrdersCostMap = getUserOrdersCostMap(userOrdersMap);
        List<Illness> illnesses = getAllIllnessesForUser(userOrdersCostMap, page);
        return IllnessDtoConverter.convertToDto(
                illnesses.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).get());
    }

    private Map<User, List<Order>> getUserOrdersMap(List<User> users, int page) {
        Map<User, List<Order>> userOrdersMap = new HashMap<>();
        users.forEach(user -> {
            Integer maxOrderSizeByUser = orderService.getCount(user.getId());
            userOrdersMap.put(user, orderService.findAll(user.getId(), page, maxOrderSizeByUser));
        });
        return userOrdersMap;
    }

    private Map<User, BigDecimal> getUserOrdersCostMap(Map<User, List<Order>> userOrdersMap) {
        Map<User, BigDecimal> userOrdersCostMap = new HashMap<>();
        userOrdersMap.forEach((user, orders) -> {
            BigDecimal orderCost = BigDecimal.valueOf(0);
            orders.forEach(order -> orderCost.add(order.getTotalPrice()));
            userOrdersCostMap.put(user, orderCost);
        });
        return userOrdersCostMap;
    }

    private List<Illness> getAllIllnessesForUser(Map<User, BigDecimal> userOrdersCostMap, int page) {
        User userWithMaxOrderCost =
                Collections.max(userOrdersCostMap.entrySet(), Comparator.comparing(Map.Entry::getValue)).getKey();

        Integer maxOrderSizeByUser = orderService.getCount(userWithMaxOrderCost.getId());

        List<Set<Doctor>> doctorsSet =
                orderService.findAll(userWithMaxOrderCost.getId(), page, maxOrderSizeByUser).stream()
                        .map(Order::getDoctors).collect(Collectors.toList());

        List<Illness> illnesses = new ArrayList<>();
        doctorsSet.forEach(doctors -> {
            doctors.forEach(doctor -> illnesses.addAll(doctor.getIllnesses()));
        });
        return illnesses;
    }
}
