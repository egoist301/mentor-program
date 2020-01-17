package com.epam.esm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IllnessRequestDto {
    @NotNull
    @Size(min = 4, max = 30)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Size(min = 4, max = 250)
    @JsonProperty("description")
    private String description;

    @NotNull
    @Min(0)
    @Max(100)
    @JsonProperty("chance_to_die")
    private Integer chanceToDie;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getChanceToDie() {
        return chanceToDie;
    }

    public void setChanceToDie(Integer chanceToDie) {
        this.chanceToDie = chanceToDie;
    }
}
