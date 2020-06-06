package com.epam.esm.facade;

import com.epam.esm.converter.DoctorDtoConverter;
import com.epam.esm.dto.doctor.DoctorPartialRequestDto;
import com.epam.esm.dto.doctor.DoctorRequestDto;
import com.epam.esm.dto.doctor.DoctorResponseDto;
import com.epam.esm.entity.Doctor;
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
        return DoctorDtoConverter.convertToDto(doctorService.findById(id));
    }

    public List<DoctorResponseDto> getAll(List<String> filtersByMainEntity, List<String> illnesses, String sortBy,
                                          String order, int page, int size) {
        return doctorService.findAll(filtersByMainEntity, illnesses, sortBy, order, page, size).stream()
                .map(DoctorDtoConverter::convertToDto).collect(Collectors.toList());
    }

    public DoctorResponseDto create(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = DoctorDtoConverter.convertToEntity(doctorRequestDto);
        doctor.setIdentificationNumber(generateIdentificationNumber(14));
        doctorService.create(doctor);
        return DoctorDtoConverter
                .convertToDto(doctorService.findByIdentificationNumber(doctor.getIdentificationNumber()));
    }

    public DoctorResponseDto update(Long id, DoctorRequestDto doctorRequestDto) {
        Doctor doctor = DoctorDtoConverter.convertToEntity(doctorRequestDto);
        doctor.setId(id);
        doctorService.update(doctor);
        return DoctorDtoConverter.convertToDto(doctorService.findById(id));
    }

    public DoctorResponseDto partialUpdate(Long id, DoctorPartialRequestDto doctorPartialRequestDto) {
        Doctor doctor = DoctorDtoConverter.partialConvertToEntity(doctorPartialRequestDto);
        doctor.setId(id);
        doctorService.partialUpdate(doctor);
        return DoctorDtoConverter.convertToDto(doctorService.findById(id));
    }

    public void delete(Long id) {
        doctorService.delete(id);
    }

    public Long getCount() {
        return doctorService.findCountDoctors();
    }

    private String generateIdentificationNumber(int length) {
        String number = NumberGenerator.generateIdentificationNumber(length);
        if (doctorService.existByIdentificationNumber(number)) {
            return generateIdentificationNumber(length);
        }
        return number;
    }
}
