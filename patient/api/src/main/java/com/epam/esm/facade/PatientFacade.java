package com.epam.esm.facade;

import com.epam.esm.controller.converter.PatientDtoConverter;
import com.epam.esm.controller.dto.PatientPartialRequestDto;
import com.epam.esm.controller.dto.PatientRequestDto;
import com.epam.esm.controller.dto.PatientResponseDto;
import com.epam.esm.exception.AnyPatientExistWithSameIdentificationNumberException;
import com.epam.esm.exception.ParseDateException;
import com.epam.esm.exception.PatientAlreadyExistException;
import com.epam.esm.exception.PatientNotExistException;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.service.IllnessService;
import com.epam.esm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientFacade {
    private PatientService patientService;
    private IllnessService illnessService;

    @Autowired
    public PatientFacade(PatientService patientService, IllnessService illnessService) {
        this.patientService = patientService;
        this.illnessService = illnessService;
    }

    public PatientResponseDto get(Long id) {
        return PatientDtoConverter.convertToDto(patientService.get(id));
    }

    public List<PatientResponseDto> getAll(String searchByFirstName, String searchByLastName, String searchByMiddleName,
                                           String searchByIllnessName, String sortBy, String order) {
        return patientService
                .getAll(searchByFirstName, searchByLastName, searchByMiddleName, searchByIllnessName, sortBy, order)
                .stream().map(PatientDtoConverter::convertToDto).collect(Collectors.toList());
    }

    public PatientResponseDto create(PatientRequestDto patientRequestDto) {
        try {
            Patient patient = PatientDtoConverter.convertToEntity(patientRequestDto);
            if (patientService.isPatientExist(patient.getIdentificationNumber())) {
                throw new PatientAlreadyExistException();
            } else {
                patientService.create(patient);
                patient.setId(patientService.findByIdentificationNumber(patient.getIdentificationNumber()).getId());
                patient.getIllnesses().forEach(illness -> {
                    if (!illnessService.isIllnessExist(illness.getName())) {
                        illnessService.create(illness);
                    }
                    illness.setId(illnessService.findByName(illness.getName()).get(0).getId());
                    patientService.saveRefPatientIlness(patient.getId(), illness.getId());
                });
                return PatientDtoConverter
                        .convertToDto(patientService.findByIdentificationNumber(patient.getIdentificationNumber()));
            }
        } catch (ParseException e) {
            throw new ParseDateException();
        }
    }

    public PatientResponseDto update(Long id, PatientRequestDto patientRequestDto) {
        try {
            Patient patient = PatientDtoConverter.convertToEntity(patientRequestDto);
            patient.setId(id);
            if (!patientService.isPatientExist(patient.getId())) {
                throw new PatientNotExistException();
            } else if (patientService
                    .isAnyPatientExistWithIdentificationNumber(patient.getId(), patient.getIdentificationNumber())) {
                throw new AnyPatientExistWithSameIdentificationNumberException();
            } else {
                patientService.update(patient);
                patient.getIllnesses().forEach(illness -> {
                    if (!illnessService.isIllnessExist(illness.getName())) {
                        illnessService.create(illness);
                    } else {
                        illness.setId(illnessService.findByName(illness.getName()).get(0).getId());
                    }
                    patientService.saveRefPatientIlness(patient.getId(), illness.getId());
                });
                return PatientDtoConverter.convertToDto(patientService.get(id));
            }
        } catch (ParseException e) {
            throw new ParseDateException();
        }
    }


    public void delete(Long id) {
        patientService.delete(id);
    }


    public PatientResponseDto partialUpdate(Long id, PatientPartialRequestDto patientPartialRequestDto) {
        try {
            Patient patient = PatientDtoConverter.partialConvertToEntity(patientPartialRequestDto);
            patient.setId(id);
            patientService.partialUpdate(patient);
            return PatientDtoConverter.convertToDto(patientService.get(id));
        } catch (ParseException e) {
            throw new ParseDateException();
        }
    }
}
