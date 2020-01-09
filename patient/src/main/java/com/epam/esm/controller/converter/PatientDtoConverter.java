package com.epam.esm.controller.converter;

import com.epam.esm.controller.dto.PatientDto;
import com.epam.esm.repository.entity.Patient;

import java.text.ParseException;

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
        return patient;
    }
}
