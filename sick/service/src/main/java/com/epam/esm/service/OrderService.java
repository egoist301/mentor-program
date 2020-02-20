package com.epam.esm.service;

import com.epam.esm.dao.DoctorDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityIsNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    private OrderDao orderDao;
    private DoctorDao doctorDao;

    @Autowired
    public OrderService(OrderDao orderDao, DoctorDao doctorDao) {
        this.orderDao = orderDao;
        this.doctorDao = doctorDao;
    }

    public Order findById(Long id) {
        if (orderDao.existsById(id)) {
            return orderDao.findById(id);
        } else {
            throw new EntityIsNotExistException("order is not exist");
        }
    }

    public List<Order> findAllForCurrentUser(Long userId, int page, int size) {
        return orderDao.findAllForCurrentUser(userId, page, size);
    }

    @Transactional
    public void create(Order order) {
        fillDoctors(order);
        order.setTotalPrice(calculateTotalPrice(order.getDoctors()));
        orderDao.create(order);
    }

    private BigDecimal calculateTotalPrice(Set<Doctor> doctors) {
        BigDecimal price = BigDecimal.valueOf(0);
        for (Doctor doctor : doctors) {
            price = price.add(doctor.getPricePerConsultation());
        }
        return price;
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

    public List<Order> findAll(int page, int size) {
        return orderDao.findAll(page, size);
    }

    public Order findByIdForCurrentUser(Long orderId, Long userId) {
        if (orderDao.existsByIdForCurrentUser(orderId, userId)) {
            return orderDao.findById(orderId);
        } else {
            throw new EntityIsNotExistException("order is not exist");
        }
    }
}
