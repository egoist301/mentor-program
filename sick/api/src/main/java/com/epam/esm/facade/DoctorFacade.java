package com.epam.esm.facade;

import com.epam.esm.converter.DoctorDtoConverter;
import com.epam.esm.dto.doctor.DoctorRequestDto;
import com.epam.esm.dto.doctor.DoctorResponseDto;
import com.epam.esm.entity.Doctor;
import com.epam.esm.exception.EntityIsNotExistException;
import com.epam.esm.service.DoctorService;
import com.epam.esm.util.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorFacade {
    private DoctorService doctorService;

    @Autowired
    public DoctorFacade(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public DoctorResponseDto get(Long id) {
        if (doctorService.isDoctorExist(id)) {
            return DoctorDtoConverter.convertToDto(doctorService.findById(id));
        } else {
            throw new EntityIsNotExistException("doctor is not exist");
        }

    }

    public List<DoctorResponseDto> getAll(List<String> filters, String sortBy, String order) {
        return doctorService.findAll(filters, sortBy, order).stream().map(DoctorDtoConverter::convertToDto).collect(
                Collectors.toList());
    }

    public DoctorResponseDto create(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = DoctorDtoConverter.convertToEntity(doctorRequestDto);
        doctor.setIdentificationNumber(NumberGenerator.generateIdentificationNumber(14));
        doctorService.fillExistIllnesses(doctor);
        doctorService.create(doctor);
        return DoctorDtoConverter
                .convertToDto(doctorService.findByIdentificationNumber(doctor.getIdentificationNumber()));
    }

    public DoctorResponseDto update(Long id, DoctorRequestDto doctorRequestDto) {
        Doctor doctor = DoctorDtoConverter.convertToEntity(doctorRequestDto);
        doctor.setId(id);
        if (doctorService.isDoctorExist(id)) {
            doctorService.fillExistIllnesses(doctor);
            doctorService.update(doctor);
            return DoctorDtoConverter.convertToDto(doctorService.findById(id));
        } else {
            throw new EntityIsNotExistException("doctor is not exist");
        }
    }

    public void delete(Long id) {
        if (doctorService.isDoctorExist(id)) {
            doctorService.delete(id);
        } else {
            throw new EntityIsNotExistException("doctor is not exist");
        }

    }

    private String generateIdentificationNumber(int length) {
        String number = NumberGenerator.generateIdentificationNumber(length);
        if (doctorService.existByIdentificationNumber(number)) {
            return generateIdentificationNumber(length);
        }
        return number;
    }
}
