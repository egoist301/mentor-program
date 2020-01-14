package com.epam.esm.repository.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Illness {
    private Long id;
    private String name;
    private String nameInLatin;
    private Integer chanceToDie;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Illness)) {
            return false;
        }
        Illness illness = (Illness) o;
        return Objects.equals(getChanceToDie(), illness.getChanceToDie()) &&
                Objects.equals(getId(), illness.getId()) &&
                Objects.equals(getName(), illness.getName()) &&
                Objects.equals(getNameInLatin(), illness.getNameInLatin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getNameInLatin(), getChanceToDie());
    }

    @Override
    public String toString() {
        return "Illness{" + "id=" + id +
                ", name='" + name + '\'' +
                ", nameInLatin='" + nameInLatin + '\'' +
                ", chanceToDie=" + chanceToDie +
                '}';
    }
}
