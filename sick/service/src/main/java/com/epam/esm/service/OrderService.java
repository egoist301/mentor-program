package com.epam.esm.service;

import com.epam.esm.converter.OrderDtoConverter;
import com.epam.esm.dao.DoctorDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.order.OrderRequestDto;
import com.epam.esm.dto.order.OrderResponseDto;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityIsNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderDao orderDao;
    private DoctorDao doctorDao;
    private UserDao userDao;

    @Autowired
    public OrderService(OrderDao orderDao, DoctorDao doctorDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.doctorDao = doctorDao;
        this.userDao = userDao;
    }

    public OrderResponseDto findById(Long id, Long userId) {
        isOrderNotExist(id);
        return OrderDtoConverter.convertToDto(orderDao.findById(id, userId));
    }

    public List<OrderResponseDto> getAll(Long userId) {
        return orderDao.findAll(userId).stream().map(OrderDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public void create(OrderRequestDto orderRequestDto, Long userId) {
        Order order = OrderDtoConverter.convertToEntity(orderRequestDto);
        fillDoctors(order);
        order.setUser(userDao.findById(userId));
        orderDao.create(order);

    }

    @Transactional
    public void update(Long id, Long userId, OrderRequestDto orderRequestDto) {
        isOrderNotExist(id);
        Order order = OrderDtoConverter.convertToEntity(orderRequestDto);
        order.setId(id);
        order.setUser(userDao.findById(userId));
        fillDoctors(order);
        orderDao.update(order);
    }

    @Transactional
    public void delete(Long id, Long userId) {
        isOrderNotExist(id);
        orderDao.delete(id);
    }

    private void fillDoctors(Order order) {
        order.getDoctors().forEach(doctor -> {
            Doctor temp = doctorDao.findById(doctor.getId());
            doctor.setIdentificationNumber(temp.getIdentificationNumber());
            doctor.setPricePerConsultation(temp.getPricePerConsultation());
            doctor.setFirstName(temp.getFirstName());
            doctor.setMiddleName(temp.getMiddleName());
            doctor.setLastName(temp.getLastName());
            doctor.setPhoneNumber(temp.getPhoneNumber());
            doctor.setDateOfBirth(temp.getDateOfBirth());
            doctor.setIllnesses(temp.getIllnesses());
        });
    }

    private void isOrderNotExist(Long id) {
        if (orderDao.existsById(id).isEmpty()) {
            throw new EntityIsNotExistException("order is not exist");
        }
    }
}
