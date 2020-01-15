package com.epam.esm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class IllnessDtoPatch {
    @Min(1)
    @JsonProperty("id")
    private Long id;
    @Size(min = 4, max = 30)
    @JsonProperty("name")
    private String name;
    @Size(min = 4, max = 60)
    @JsonProperty("name_in_latin")
    private String nameInLatin;
    @Min(0)
    @Max(100)
    @JsonProperty("chance_to_die")
    private Integer chanceToDie;

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

    public Integer getChanceToDie() {
        return chanceToDie;
    }

    public void setChanceToDie(Integer chanceToDie) {
        this.chanceToDie = chanceToDie;
    }
}
