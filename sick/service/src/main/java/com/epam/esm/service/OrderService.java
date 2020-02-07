package com.epam.esm.service;

import com.epam.esm.dao.DoctorDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return orderDao.findById(id);
    }

    public List<Order> findAll(Long userId, int page, int size) {
        return orderDao.findAll(userId, page, size);
    }

    @Transactional
    public void create(Order order) {
        orderDao.create(order);

    }

    /*@Transactional
    public void update(Order order) {
        orderDao.update(order);
    }

    @Transactional
    public void delete(Long id) {
        orderDao.delete(id);
    }*/

    public void fillDoctors(Order order) {
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

    public boolean isOrderExist(Long id) {
        return orderDao.existsById(id);
    }
}
