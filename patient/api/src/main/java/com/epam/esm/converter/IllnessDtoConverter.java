package com.epam.esm.converter;

import com.epam.esm.dto.IllnessPartialRequestDto;
import com.epam.esm.dto.IllnessRequestDto;
import com.epam.esm.dto.IllnessResponseDto;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.util.DateFormatter;

public final class IllnessDtoConverter {
    private IllnessDtoConverter() {
    }

    public static IllnessResponseDto convertToDto(Illness illness) {
        IllnessResponseDto illnessResponseDto = new IllnessResponseDto();
        illnessResponseDto.setId(illness.getId());
        illnessResponseDto.setName(illness.getName());
        illnessResponseDto.setDescription(illness.getDescription());
        illnessResponseDto.setChanceToDie(illness.getChanceToDie());
        if (illness.getUpdateDate() != null) {
            illnessResponseDto.setUpdateDate(DateFormatter.convertDateToString(illness.getUpdateDate()));
        }
        if (illness.getCreateDate() != null) {
            illnessResponseDto.setCreateDate(DateFormatter.convertDateToString(illness.getCreateDate()));
        }
        return illnessResponseDto;
    }

    public static Illness convertToEntity(IllnessRequestDto illnessRequestDto) {
        Illness illness = new Illness();
        illness.setName(illnessRequestDto.getName());
        illness.setDescription(illnessRequestDto.getDescription());
        illness.setChanceToDie(illnessRequestDto.getChanceToDie());
        return illness;
    }

    public static Illness partialConvertToEntity(IllnessPartialRequestDto illnessPartialRequestDto) {
        Illness illness = new Illness();
        illness.setName(illnessPartialRequestDto.getName());
        illness.setDescription(illnessPartialRequestDto.getDescription());
        illness.setChanceToDie(illnessPartialRequestDto.getChanceToDie());
        return illness;
    }
}
