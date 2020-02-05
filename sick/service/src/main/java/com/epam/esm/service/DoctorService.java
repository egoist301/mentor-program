package com.epam.esm.service;

import com.epam.esm.converter.DoctorDtoConverter;
import com.epam.esm.dao.DoctorDao;
import com.epam.esm.dao.IllnessDao;
import com.epam.esm.dto.doctor.DoctorRequestDto;
import com.epam.esm.dto.doctor.DoctorResponseDto;
import com.epam.esm.entity.Doctor;
import com.epam.esm.entity.Illness;
import com.epam.esm.exception.EntityIsNotExistException;
import com.epam.esm.util.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public DoctorResponseDto findById(Long id) {
        isDoctorNotExist(id);
        return DoctorDtoConverter.convertToDto(doctorDao.findById(id));
    }

    public List<DoctorResponseDto> getAll(List<String> filters, String sortBy, String order) {
        return doctorDao.findAll(filters, sortBy, order).stream().map(DoctorDtoConverter::convertToDto).collect(
                Collectors.toList());
    }

    @Transactional
    public DoctorResponseDto create(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = DoctorDtoConverter.convertToEntity(doctorRequestDto);
        doctor.setIdentificationNumber(NumberGenerator.generateIdentificationNumber(14));
        fillExistIllnesses(doctor);
        doctorDao.create(doctor);
        return DoctorDtoConverter.convertToDto(doctor);
    }

    private String generateIdentificationNumber(int length) {
        String number = NumberGenerator.generateIdentificationNumber(length);
        if (!doctorDao.existByIdentificationNumber(number).isEmpty()) {
            return generateIdentificationNumber(length);
        }
        return number;
    }

    @Transactional
    public DoctorResponseDto update(Long id, DoctorRequestDto doctorRequestDto) {
        Doctor doctor = DoctorDtoConverter.convertToEntity(doctorRequestDto);
        doctor.setId(id);
        isDoctorNotExist(id);
        doctor.setIdentificationNumber(doctorDao.findById(id).getIdentificationNumber());
        fillExistIllnesses(doctor);
        doctorDao.update(doctor);
        return null;
    }

    @Transactional
    public void delete(Long id) {
        isDoctorNotExist(id);
        doctorDao.delete(id);
    }

    private void fillExistIllnesses(Doctor doctor) {
        if (doctor.getIllnesses() != null) {
            doctor.getIllnesses().forEach(illness -> {
                if (!illnessDao.existsByName(illness.getName()).isEmpty()) {
                    Illness illnessFromDB = illnessDao.findByName(illness.getName());
                    illness.setId(illnessFromDB.getId());
                    illness.setCreateDate(illnessFromDB.getCreateDate());
                    illness.setUpdateDate(illnessFromDB.getUpdateDate());
                }
            });
        }
    }

    private void isDoctorNotExist(Long id) {
        if (doctorDao.existsById(id).isEmpty()) {
            throw new EntityIsNotExistException("doctor is not exist");
        }
    }
}
