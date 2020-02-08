package com.epam.esm.converter;

import com.epam.esm.dto.doctor.DoctorPartialRequestDto;
import com.epam.esm.dto.doctor.DoctorRequestDto;
import com.epam.esm.dto.doctor.DoctorResponseDto;
import com.epam.esm.entity.Doctor;

import java.util.stream.Collectors;

public final class DoctorDtoConverter {
    private DoctorDtoConverter() {
    }

    public static DoctorResponseDto convertToDto(Doctor doctor) {
        DoctorResponseDto doctorResponseDto = new DoctorResponseDto();
        doctorResponseDto.setId(doctor.getId());
        doctorResponseDto.setFirstName(doctor.getFirstName());
        doctorResponseDto.setLastName(doctor.getLastName());
        doctorResponseDto.setMiddleName(doctor.getMiddleName());
        doctorResponseDto.setPhoneNumber(doctor.getPhoneNumber());
        doctorResponseDto.setPricePerConsultation(doctor.getPricePerConsultation());
        doctorResponseDto.setDateOfBirth(doctor.getDateOfBirth());
        doctorResponseDto.setIdentificationNumber(doctor.getIdentificationNumber());
        doctorResponseDto.setCreateDate(doctor.getCreateDate());
        doctorResponseDto.setUpdateDate(doctor.getUpdateDate());
        doctorResponseDto.setIllnesses(doctor.getIllnesses().stream().map(IllnessDtoConverter::convertToDto).collect(
                Collectors.toSet()));
        return doctorResponseDto;
    }

    public static Doctor convertToEntity(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorRequestDto.getFirstName());
        doctor.setMiddleName(doctorRequestDto.getMiddleName());
        doctor.setLastName(doctorRequestDto.getLastName());
        doctor.setPhoneNumber(doctorRequestDto.getPhoneNumber().intValue());
        doctor.setDateOfBirth(doctorRequestDto.getDateOfBirth());
        doctor.setPricePerConsultation(doctorRequestDto.getPricePerConsultation());
        if (doctorRequestDto.getIllnesses() != null) {
            doctor.setIllnesses(
                    doctorRequestDto.getIllnesses().stream().map(IllnessDtoConverter::convertToEntity).collect(
                            Collectors.toSet()));
        }
        return doctor;
    }

    public static Doctor partialConvertToEntity(DoctorPartialRequestDto doctorPartialRequestDto) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorPartialRequestDto.getFirstName());
        doctor.setMiddleName(doctorPartialRequestDto.getMiddleName());
        doctor.setLastName(doctorPartialRequestDto.getLastName());
        doctor.setPhoneNumber(doctorPartialRequestDto.getPhoneNumber());
        doctor.setDateOfBirth(doctorPartialRequestDto.getDateOfBirth());
        doctor.setPricePerConsultation(doctorPartialRequestDto.getPricePerConsultation());
        if (doctorPartialRequestDto.getIllnesses() != null) {
            doctor.setIllnesses(
                    doctorPartialRequestDto.getIllnesses().stream().map(IllnessDtoConverter::partialConvertToEntity)
                            .collect(Collectors.toSet()));
        }
        return doctor;
    }
}
