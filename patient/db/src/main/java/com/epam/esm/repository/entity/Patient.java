package com.epam.esm.repository.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Component
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer phoneNumber;
    private LocalDate dateOfBirth;
    private String identificationNumber;
    private LocalDate createDate;
    private LocalDate updateDate;
    private Set<Illness> illnesses;

    public Patient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
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

    public Set<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(
            Set<Illness> illnesses) {
        this.illnesses = illnesses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) o;
        return Objects.equals(getId(), patient.getId()) &&
                Objects.equals(getFirstName(), patient.getFirstName()) &&
                Objects.equals(getLastName(), patient.getLastName()) &&
                Objects.equals(getMiddleName(), patient.getMiddleName()) &&
                Objects.equals(getPhoneNumber(), patient.getPhoneNumber()) &&
                Objects.equals(getDateOfBirth(), patient.getDateOfBirth()) &&
                Objects.equals(getIdentificationNumber(), patient.getIdentificationNumber()) &&
                Objects.equals(getCreateDate(), patient.getCreateDate()) &&
                Objects.equals(getUpdateDate(), patient.getUpdateDate()) &&
                Objects.equals(getIllnesses(), patient.getIllnesses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getMiddleName(), getPhoneNumber(), getDateOfBirth(),
                getIdentificationNumber(), getCreateDate(), getUpdateDate(), getIllnesses());
    }
}
