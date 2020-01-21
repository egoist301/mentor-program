package com.epam.esm.converter;

import com.epam.esm.dto.PatientPartialRequestDto;
import com.epam.esm.dto.PatientRequestDto;
import com.epam.esm.dto.PatientResponseDto;
import com.epam.esm.repository.entity.Patient;

import java.util.stream.Collectors;

public final class PatientDtoConverter {
    private PatientDtoConverter() {
    }

    public static PatientResponseDto convertToDto(Patient patient) {
        PatientResponseDto patientResponseDto = new PatientResponseDto();
        patientResponseDto.setId(patient.getId());
        patientResponseDto.setFirstName(patient.getFirstName());
        patientResponseDto.setLastName(patient.getLastName());
        patientResponseDto.setMiddleName(patient.getMiddleName());
        patientResponseDto.setPhoneNumber(patient.getPhoneNumber());
        patientResponseDto.setDateOfBirth(patient.getDateOfBirth());
        patientResponseDto.setIdentificationNumber(patient.getIdentificationNumber());
        patientResponseDto.setCreateDate(patient.getCreateDate());
        patientResponseDto.setUpdateDate(patient.getUpdateDate());
        patientResponseDto.setIllnesses(patient.getIllnesses().stream().map(IllnessDtoConverter::convertToDto).collect(
                Collectors.toSet()));
        return patientResponseDto;
    }

    public static Patient convertToEntity(PatientRequestDto patientRequestDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientRequestDto.getFirstName());
        patient.setMiddleName(patientRequestDto.getMiddleName());
        patient.setLastName(patientRequestDto.getLastName());
        patient.setPhoneNumber(patientRequestDto.getPhoneNumber());
        patient.setDateOfBirth(patientRequestDto.getDateOfBirth());
        patient.setIdentificationNumber(patientRequestDto.getIdentificationNumber());
        if (patientRequestDto.getIllnesses() != null) {
            patient.setIllnesses(
                    patientRequestDto.getIllnesses().stream().map(IllnessDtoConverter::convertToEntity).collect(
                            Collectors.toSet()));
        }
        return patient;
    }

    public static Patient partialConvertToEntity(PatientPartialRequestDto patientPartialRequestDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientPartialRequestDto.getFirstName());
        patient.setMiddleName(patientPartialRequestDto.getMiddleName());
        patient.setLastName(patientPartialRequestDto.getLastName());
        patient.setPhoneNumber(patientPartialRequestDto.getPhoneNumber());
        patient.setDateOfBirth(patientPartialRequestDto.getDateOfBirth());
        patient.setIdentificationNumber(patientPartialRequestDto.getIdentificationNumber());
        if (patientPartialRequestDto.getIllnesses() != null) {
            patient.setIllnesses(
                    patientPartialRequestDto.getIllnesses().stream().map(IllnessDtoConverter::partialConvertToEntity)
                            .collect(Collectors.toSet()));
        }
        return patient;
    }
}
