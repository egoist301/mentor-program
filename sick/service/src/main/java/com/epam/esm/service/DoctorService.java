package com.epam.esm.service;

import com.epam.esm.dao.DoctorDao;
import com.epam.esm.dao.IllnessDao;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Illness;
import com.epam.esm.util.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {
    private DoctorDao doctorDao;
    private IllnessDao illnessDao;

    @Autowired
    public DoctorService(DoctorDao doctorDao, IllnessDao illnessDao) {
        this.doctorDao = doctorDao;
        this.illnessDao = illnessDao;
    }

    public Doctor findById(Long id) {
        return doctorDao.findById(id);
    }

    public List<Doctor> findAll(List<String> filters, String sortBy, String order) {
        return doctorDao.findAll(filters, sortBy, order);
    }

    @Transactional
    public void create(Doctor doctor) {
        doctorDao.create(doctor);
    }

    @Transactional
    public void update(Doctor doctor) {
        doctorDao.update(doctor);
    }

    @Transactional
    public void delete(Long id) {
        doctorDao.delete(id);
    }

    public void fillExistIllnesses(Doctor doctor) {
        if (doctor.getIllnesses() != null) {
            doctor.getIllnesses().forEach(illness -> {
                if (illnessDao.existsByName(illness.getName())) {
                    Illness illnessFromDB = illnessDao.findByName(illness.getName());
                    illness.setId(illnessFromDB.getId());
                    illness.setCreateDate(illnessFromDB.getCreateDate());
                    illness.setUpdateDate(illnessFromDB.getUpdateDate());
                }
            });
        }
    }

    public boolean isDoctorExist(Long id) {
        return doctorDao.existsById(id);
    }

    public Doctor findByIdentificationNumber(String identificationNumber) {
        return doctorDao.findByIdentificationNumber(identificationNumber);
    }

    public boolean existByIdentificationNumber(String number) {
        return doctorDao.existByIdentificationNumber(number);
    }
}
