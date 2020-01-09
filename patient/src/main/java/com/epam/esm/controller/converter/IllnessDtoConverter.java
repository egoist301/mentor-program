package com.epam.esm.controller.converter;

import com.epam.esm.controller.dto.IllnessDto;
import com.epam.esm.repository.entity.Illness;

public final class IllnessDtoConverter {
    private IllnessDtoConverter() {
    }

    public static IllnessDto convertToDto(Illness illness) {
        IllnessDto illnessDto = new IllnessDto();
        illnessDto.setId(illness.getId());
        illnessDto.setChanceToDie(illness.getChanceToDie());
        illnessDto.setName(illness.getName());
        illnessDto.setNameInLatin(illness.getNameInLatin());
        return illnessDto;
    }

    public static Illness convertToEntity(IllnessDto illnessDto) {
        Illness illness = new Illness();
        illness.setName(illnessDto.getName());
        illness.setNameInLatin(illnessDto.getNameInLatin());
        illness.setChanceToDie(illnessDto.getChanceToDie());
        illness.setId(illnessDto.getId());
        return illness;
    }
}
