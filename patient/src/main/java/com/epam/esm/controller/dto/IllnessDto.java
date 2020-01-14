package com.epam.esm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IllnessDto {
    @Min(1)
    private Long id;
    @NotNull
    @Size(min = 4, max = 30)
    private String name;
    @JsonProperty("name_in_latin")
    @NotNull
    @Size(min = 4, max = 60)
    private String nameInLatin;
    @JsonProperty("chance_to_die")
    @Min(0)
    @Max(100)
    private int chanceToDie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameInLatin() {
        return nameInLatin;
    }

    public void setNameInLatin(String nameInLatin) {
        this.nameInLatin = nameInLatin;
    }

    public int getChanceToDie() {
        return chanceToDie;
    }

    public void setChanceToDie(int chanceToDie) {
        this.chanceToDie = chanceToDie;
    }
}
