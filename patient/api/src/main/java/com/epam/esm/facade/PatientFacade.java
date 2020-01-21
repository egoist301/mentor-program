package com.epam.esm.facade;

import com.epam.esm.converter.PatientDtoConverter;
import com.epam.esm.dto.PatientPartialRequestDto;
import com.epam.esm.dto.PatientRequestDto;
import com.epam.esm.dto.PatientResponseDto;
import com.epam.esm.exception.AnyPatientExistWithSameIdentificationNumberException;
import com.epam.esm.exception.PatientAlreadyExistException;
import com.epam.esm.exception.PatientNotExistException;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.service.IllnessService;
import com.epam.esm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        Patient patient = patientService.get(id);
        if (patient == null) {
            return null;
        }
        patient.setIllnesses(illnessService.findByPatientId(id));
        return PatientDtoConverter.convertToDto(patient);
    }

    public List<PatientResponseDto> getAll(String searchByFirstName, String searchByLastName, String searchByMiddleName,
                                           String searchByIllnessName, String sortBy, String order) {
        List<Patient> patients = patientService
                .getAll(searchByFirstName, searchByLastName, searchByMiddleName, searchByIllnessName, sortBy, order);
        patients.forEach(patient -> patient.setIllnesses(illnessService.findByPatientId(patient.getId())));
        return patients.stream().map(PatientDtoConverter::convertToDto).collect(Collectors.toList());
    }

    public PatientResponseDto create(PatientRequestDto patientRequestDto) {
        Patient patient = PatientDtoConverter.convertToEntity(patientRequestDto);
        if (patientService.isPatientExist(patient.getIdentificationNumber())) {
            throw new PatientAlreadyExistException();
        } else {
            patientService.create(patient);
            Patient patientToResponse =
                    patientService.findByIdentificationNumber(patient.getIdentificationNumber());
            patient.setId(patientToResponse.getId());
            if (patient.getIllnesses() != null) {
                patient.getIllnesses().forEach(illness -> {
                    if (!illnessService.isIllnessExist(illness.getName())) {
                        illnessService.create(illness);
                    }
                    illness.setId(illnessService.findByName(illness.getName()).getId());
                    patientService.saveRefPatientIlness(patient.getId(), illness.getId());
                });
            }
            patientToResponse.setIllnesses(illnessService.findByPatientId(patientToResponse.getId()));
            return PatientDtoConverter.convertToDto(patientToResponse);
        }
    }

    public PatientResponseDto update(Long id, PatientRequestDto patientRequestDto) {
        Patient patient = PatientDtoConverter.convertToEntity(patientRequestDto);
        patient.setId(id);
        if (!patientService.isPatientExist(patient.getId())) {
            throw new PatientNotExistException();
        } else if (patientService
                .isAnyPatientExistWithIdentificationNumber(id, patient.getIdentificationNumber())) {
            throw new AnyPatientExistWithSameIdentificationNumberException();
        } else {
            patientService.update(patient);

            Patient patientToResponse = patientService.get(id);

            solveRefPatientIllness(patient, id);
            patientToResponse.setIllnesses(illnessService.findByPatientId(id));
            return PatientDtoConverter.convertToDto(patientToResponse);
        }
    }

    public void delete(Long id) {//проверка
        if (patientService.isPatientExist(id)) {
            patientService.delete(id);
        } else {
            throw new PatientNotExistException("patient not exist");
        }
    }

    public PatientResponseDto partialUpdate(Long id, PatientPartialRequestDto patientPartialRequestDto) {
        Patient patient = PatientDtoConverter.partialConvertToEntity(patientPartialRequestDto);
        patient.setId(id);

        if (!patientService.isPatientExist(patient.getId())) {
            throw new PatientNotExistException();
        } else if (patientService
                .isAnyPatientExistWithIdentificationNumber(id, patient.getIdentificationNumber())) {
            throw new AnyPatientExistWithSameIdentificationNumberException();
        } else {
            patientService.partialUpdate(patient);
            Patient patientToResponse = patientService.get(id);
            solveRefPatientIllness(patient, id);
            patientToResponse.setIllnesses(illnessService.findByPatientId(id));
            return PatientDtoConverter.convertToDto(patientToResponse);
        }
    }

    private void solveRefPatientIllness(Patient patient, Long id) {
        List<Illness> oldIllnesses = new ArrayList<>(illnessService.findByPatientId(id));
        if (patient.getIllnesses() != null) {
            patient.getIllnesses().forEach(illness -> {
                if (!illnessService.isIllnessExist(illness.getName())) {
                    illnessService.create(illness);
                    Illness temp = illnessService.findByName(illness.getName());
                    illness.setId(temp.getId());
                    illness.setCreateDate(temp.getCreateDate());
                    illness.setUpdateDate(temp.getUpdateDate());
                    patientService.saveRefPatientIlness(id, illness.getId());
                } else {
                    Illness temp = illnessService.findByName(illness.getName());
                    illness.setId(temp.getId());
                    illness.setCreateDate(temp.getCreateDate());
                    illness.setUpdateDate(temp.getUpdateDate());
                    if (!patientService.isRefPatientIllnessExist(id, illness.getId())) {
                        patientService.saveRefPatientIlness(id, illness.getId());
                    }
                }
            });
            oldIllnesses.removeAll(patient.getIllnesses().stream().collect(Collectors.toList()));
            oldIllnesses.forEach(illness -> patientService.removeRefPatientIllness(id, illness.getId()));
        }
    }
}
