package com.epam.esm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IllnessDto {
    private Long id;
    private String name;
    @JsonProperty("name_in_latin")
    private String nameInLatin;
    @JsonProperty("chance_to_die")
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
