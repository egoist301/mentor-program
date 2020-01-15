package com.epam.esm.controller.converter;

import com.epam.esm.controller.dto.IllnessResponseDto;
import com.epam.esm.controller.dto.IllnessDtoPatch;
import com.epam.esm.repository.entity.Illness;

public final class IllnessDtoConverter {
    private IllnessDtoConverter() {
    }

    public static IllnessResponseDto convertToDto(Illness illness) {
        IllnessResponseDto illnessResponseDto = new IllnessResponseDto();
        illnessResponseDto.setId(illness.getId());
        illnessResponseDto.setChanceToDie(illness.getChanceToDie());
        illnessResponseDto.setName(illness.getName());
        illnessResponseDto.setNameInLatin(illness.getNameInLatin());
        return illnessResponseDto;
    }

    public static Illness convertToEntity(IllnessResponseDto illnessResponseDto) {
        Illness illness = new Illness();
        illness.setName(illnessResponseDto.getName());
        illness.setNameInLatin(illnessResponseDto.getNameInLatin());
        illness.setChanceToDie(illnessResponseDto.getChanceToDie());
        illness.setId(illnessResponseDto.getId());
        return illness;
    }
    public static IllnessDtoPatch convertToDtoPatch(Illness illness) {
        IllnessDtoPatch illnessDtoPatch = new IllnessDtoPatch();
        illnessDtoPatch.setId(illness.getId());
        illnessDtoPatch.setChanceToDie(illness.getChanceToDie());
        illnessDtoPatch.setName(illness.getName());
        illnessDtoPatch.setNameInLatin(illness.getNameInLatin());
        return illnessDtoPatch;
    }

    public static Illness convertToEntityPatch(IllnessDtoPatch illnessDtoPatch) {
        Illness illness = new Illness();
        illness.setName(illnessDtoPatch.getName());
        illness.setNameInLatin(illnessDtoPatch.getNameInLatin());
        illness.setChanceToDie(illnessDtoPatch.getChanceToDie());
        illness.setId(illnessDtoPatch.getId());
        return illness;
    }
}
