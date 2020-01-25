package com.epam.esm.facade;

import com.epam.esm.converter.PatientDtoConverter;
import com.epam.esm.dto.PatientResponseDto;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.entity.Patient;
import com.epam.esm.service.IllnessService;
import com.epam.esm.service.PatientService;
import com.epam.esm.util.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RandomFacade {
    private PatientService patientService;
    private IllnessService illnessService;

    @Autowired
    public RandomFacade(PatientService patientService, IllnessService illnessService) {
        this.patientService = patientService;
        this.illnessService = illnessService;
    }

    public PatientResponseDto updateIllnesses(Long id) {
        Patient patient = patientService.get(id);
        List<Illness> illnessList = illnessService.getAll();
        int length = illnessList.size();
        int countOfIllnesses = NumberGenerator.generateNumber(length);
        List<Illness> illnesses = new ArrayList<>();
        for (int i = 0; i < countOfIllnesses; ++i) {
            long illnessId = generateId(id, length, illnessList);
            illnesses.add(illnessList.get((int) illnessId));
            patientService.saveRefPatientIllness(id, illnesses.get(i).getId());
        }
        patient.setIllnesses(new HashSet<>(illnesses));
        return PatientDtoConverter.convertToDto(patient);
    }

    private long generateId(long patientId, int length, List<Illness> illnesses) {
        long illnessId = NumberGenerator.generateNumber(length);
        if (patientService.isRefPatientIllnessExist(patientId, illnesses.get((int) illnessId).getId())) {
            return generateId(patientId, length, illnesses);
        }
        return illnessId;
    }


}
