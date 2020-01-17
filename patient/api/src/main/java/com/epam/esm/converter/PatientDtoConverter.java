package com.epam.esm.converter;

import com.epam.esm.dto.PatientPartialRequestDto;
import com.epam.esm.dto.PatientRequestDto;
import com.epam.esm.dto.PatientResponseDto;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.util.DateFormatter;

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
        patientResponseDto.setDateOfBirth(DateFormatter.convertDateToString(patient.getDateOfBirth()));
        patientResponseDto.setIdentificationNumber(patient.getIdentificationNumber());
        patientResponseDto.setCreateDate(DateFormatter.convertDateToString(patient.getCreateDate()));
        patientResponseDto.setUpdateDate(DateFormatter.convertDateToString(patient.getUpdateDate()));
        patientResponseDto.setIllnesses(patient.getIllnesses().stream().map(IllnessDtoConverter::convertToDto).collect(
                Collectors.toSet()));
        return patientResponseDto;
    }

    public static Patient convertToEntity(PatientRequestDto patientRequestDto) throws ParseException {
        Patient patient = new Patient();
        patient.setFirstName(patientRequestDto.getFirstName());
        patient.setMiddleName(patientRequestDto.getMiddleName());
        patient.setLastName(patientRequestDto.getLastName());
        patient.setPhoneNumber(patientRequestDto.getPhoneNumber());
        patient.setDateOfBirth(DateFormatter.convertStringToDate(patientRequestDto.getDateOfBirth()));
        patient.setIdentificationNumber(patientRequestDto.getIdentificationNumber());
        patient.setIllnesses(
                patientRequestDto.getIllnesses().stream().map(IllnessDtoConverter::convertToEntity).collect(
                        Collectors.toSet()));
        return patient;
    }

    public static Patient partialConvertToEntity(PatientPartialRequestDto patientPartialRequestDto)
            throws ParseException {
        Patient patient = new Patient();
        patient.setFirstName(patientPartialRequestDto.getFirstName());
        patient.setMiddleName(patientPartialRequestDto.getMiddleName());
        patient.setLastName(patientPartialRequestDto.getLastName());
        patient.setPhoneNumber(patientPartialRequestDto.getPhoneNumber());
        if (patientPartialRequestDto.getDateOfBirth() != null) {
            patient.setDateOfBirth(DateFormatter.convertStringToDate(patientPartialRequestDto.getDateOfBirth()));
        }
        if (patientPartialRequestDto.getIllnesses() != null) {
            patient.setIllnesses(
                    patientPartialRequestDto.getIllnesses().stream().map(IllnessDtoConverter::partialConvertToEntity)
                            .collect(Collectors.toSet()));
        }
        return patient;
    }
}
