package com.epam.esm.service;

import com.epam.esm.dao.DoctorDao;
import com.epam.esm.dao.IllnessDao;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Illness;
import com.epam.esm.exception.EntityIsNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (doctorDao.existsById(id)) {
            return doctorDao.findById(id);
        } else {
            throw new EntityIsNotExistException("doctor is not exist");
        }
    }

    public List<Doctor> findAll(List<String> filtersByMainEntity, List<String> illnesses, String sortBy, String order,
                                int page, int size) {
        List<Doctor> doctors = doctorDao.findAll(filtersByMainEntity, sortBy, order);
        illnesses.forEach(name -> {
            if (!illnessDao.existsByName(name)) {
                throw new EntityIsNotExistException("illness with name: '" + name + "' is not exist");
            }
        });
        doctors = doctors.stream()
                .filter(doctor -> doctor.getIllnesses()
                        .containsAll(illnesses.stream().map(name -> illnessDao.findByName(name))
                                .collect(Collectors.toList()))).collect(Collectors.toList());
        int from = Math.min((page == 1) ? page - 1 : (page - 1) * size, doctors.size());
        int to = (Math.min(size + from, doctors.size()));
        return doctors.subList(from, to);
    }

    @Transactional
    public void create(Doctor doctor) {
        fillExistIllnesses(doctor);
        doctorDao.create(doctor);
    }

    @Transactional
    public void update(Doctor doctor) {
        if (doctorDao.existsById(doctor.getId())) {
            fillExistIllnesses(doctor);
            doctorDao.update(doctor);
        } else {
            throw new EntityIsNotExistException("doctor is not exist");
        }
    }

    @Transactional
    public void partialUpdate(Doctor doctor) {
        if (doctorDao.existsById(doctor.getId())) {
            Doctor doctorFromDB = doctorDao.findById(doctor.getId());
            if (Objects.nonNull(doctor.getFirstName())) {
                doctorFromDB.setFirstName(doctor.getFirstName());
            }
            if (Objects.nonNull(doctor.getLastName())) {
                doctorFromDB.setLastName(doctor.getLastName());
            }
            if (Objects.nonNull(doctor.getMiddleName())) {
                doctorFromDB.setMiddleName(doctor.getMiddleName());
            }
            if (Objects.nonNull(doctor.getPhoneNumber())) {
                doctorFromDB.setPhoneNumber(doctor.getPhoneNumber());
            }
            if (Objects.nonNull(doctor.getPricePerConsultation())) {
                doctorFromDB.setPricePerConsultation(doctor.getPricePerConsultation());
            }
            if (Objects.nonNull(doctor.getDateOfBirth())) {
                doctorFromDB.setDateOfBirth(doctor.getDateOfBirth());
            }
            if (Objects.nonNull(doctor.getIllnesses())) {
                fillExistIllnesses(doctor);
                doctorFromDB.setIllnesses(doctor.getIllnesses());
            }
            doctorDao.update(doctorFromDB);
        } else {
            throw new EntityIsNotExistException("doctor is not exist");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (doctorDao.existsById(id)) {
            doctorDao.delete(id);
        } else {
            throw new EntityIsNotExistException("doctor is not exist");
        }
    }

    private void fillExistIllnesses(Doctor doctor) {
        if (Objects.nonNull(doctor.getIllnesses())) {
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

    public Doctor findByIdentificationNumber(String identificationNumber) {
        return doctorDao.findByIdentificationNumber(identificationNumber);
    }

    public boolean existByIdentificationNumber(String number) {
        return doctorDao.existByIdentificationNumber(number);
    }
}
