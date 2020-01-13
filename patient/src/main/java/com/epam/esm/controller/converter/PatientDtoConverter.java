package com.epam.esm.controller.converter;

import com.epam.esm.controller.dto.PatientDto;
import com.epam.esm.repository.entity.Patient;

import java.text.ParseException;
import java.util.stream.Collectors;

public final class PatientDtoConverter {
    private PatientDtoConverter() {
    }

    public static PatientDto convertToDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setMiddleName(patient.getMiddleName());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        patientDto.setDateOfBirth(patient.getDateOfBirth());
        patientDto.setIllnesses(patient.getIllnesses().stream().map(IllnessDtoConverter::convertToDto).collect(
                Collectors.toList()));
        return patientDto;
    }

    public static Patient convertToEntity(PatientDto patientDto) throws ParseException {
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setFirstName(patientDto.getFirstName());
        patient.setMiddleName(patientDto.getMiddleName());
        patient.setLastName(patientDto.getLastName());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setDateOfBirth(patientDto.getDateOfBirthConverted());
        patient.setIllnesses(patientDto.getIllnesses().stream().map(IllnessDtoConverter::convertToEntity).collect(
                Collectors.toList()));
        return patient;
    }
}
