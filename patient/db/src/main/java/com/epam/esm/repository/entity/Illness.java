package com.epam.esm.repository.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class Illness {
    private Long id;
    private String name;
    private String description;
    private Integer chanceToDie;
    private LocalDate createDate;
    private LocalDate updateDate;

    public Illness() {
    }

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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Illness)) {
            return false;
        }
        Illness illness = (Illness) o;
        return Objects.equals(getId(), illness.getId()) &&
                Objects.equals(getName(), illness.getName()) &&
                Objects.equals(getDescription(), illness.getDescription()) &&
                Objects.equals(getChanceToDie(), illness.getChanceToDie()) &&
                Objects.equals(getCreateDate(), illness.getCreateDate()) &&
                Objects.equals(getUpdateDate(), illness.getUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getChanceToDie(), getCreateDate(), getUpdateDate());
    }
}
