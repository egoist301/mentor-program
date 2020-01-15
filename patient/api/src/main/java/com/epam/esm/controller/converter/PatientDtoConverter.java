package com.epam.esm.controller.converter;

import com.epam.esm.controller.dto.PatientResponseDto;
import com.epam.esm.repository.entity.Patient;

import java.text.ParseException;
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
        patientResponseDto.setIllnesses(patient.getIllnesses().stream().map(IllnessDtoConverter::convertToDto).collect(
                Collectors.toList()));
        return patientResponseDto;
    }

    public static Patient convertToEntity(PatientResponseDto patientResponseDto) throws ParseException {
        Patient patient = new Patient();
        patient.setId(patientResponseDto.getId());
        patient.setFirstName(patientResponseDto.getFirstName());
        patient.setMiddleName(patientResponseDto.getMiddleName());
        patient.setLastName(patientResponseDto.getLastName());
        patient.setPhoneNumber(patientResponseDto.getPhoneNumber());
        patient.setDateOfBirth(patientResponseDto.getDateOfBirthConverted());
        patient.setIllnesses(patientResponseDto.getIllnesses().stream().map(IllnessDtoConverter::convertToEntity).collect(
                Collectors.toList()));
        return patient;
    }
}
