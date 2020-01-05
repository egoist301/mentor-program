package com.epam.esm.repository.entity;

import java.util.Objects;

public class Illness extends Entity {
    private String name;
    private String nameInLatin;
    private int chanceToDie;

    public Illness() {
    }

    public Illness(final Long id, final String name, final String nameInLatin, final int chanceToDie) {
        super(id);
        this.name = name;
        this.nameInLatin = nameInLatin;
        this.chanceToDie = chanceToDie;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getNameInLatin() {
        return nameInLatin;
    }

    public void setNameInLatin(final String nameInLatin) {
        this.nameInLatin = nameInLatin;
    }

    public int getChanceToDie() {
        return chanceToDie;
    }

    public void setChanceToDie(final int chanceToDie) {
        this.chanceToDie = chanceToDie;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Illness)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Illness illness = (Illness) o;
        return getChanceToDie() == illness.getChanceToDie() &&
                Objects.equals(getName(), illness.getName()) &&
                Objects.equals(getNameInLatin(), illness.getNameInLatin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getNameInLatin(), getChanceToDie());
    }

    @Override
    public String toString() {
        return "Illness{" + super.toString() +
                ", name='" + name + '\'' +
                ", nameInLatin='" + nameInLatin + '\'' +
                ", chanceToDie=" + chanceToDie +
                '}';
    }
}
