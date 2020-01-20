package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IllnessRequestDto {
    @NotNull(message = "name can't be null")
    @Size(min = 4, max = 30, message = "name can be 4 to 30 characters long")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "description can't be null")
    @Size(min = 4, max = 250, message = "description can be 4 to 250 characters long")
    @JsonProperty("description")
    private String description;

    @NotNull(message = "chance to die can't be null")
    @Min(value = 0, message = "minimum 0%")
    @Max(value = 100, message = "maximum 100%")
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
