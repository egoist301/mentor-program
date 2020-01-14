package com.epam.esm.repository.entity;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer phoneNumber;
    private Date dateOfBirth;
    private List<Illness> illnesses;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<Illness> illnessesNew) {
        illnesses = illnessesNew;
    }

    @Override
    public boolean equals(Object oNew) {
        if (this == oNew) {
            return true;
        }
        if (!(oNew instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) oNew;
        return Objects.equals(getPhoneNumber(), patient.getPhoneNumber()) &&
                Objects.equals(getId(), patient.getId()) &&
                Objects.equals(getFirstName(), patient.getFirstName()) &&
                Objects.equals(getLastName(), patient.getLastName()) &&
                Objects.equals(getMiddleName(), patient.getMiddleName()) &&
                Objects.equals(getDateOfBirth(), patient.getDateOfBirth()) &&
                Objects.equals(getIllnesses(), patient.getIllnesses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getMiddleName(), getPhoneNumber(), getDateOfBirth(),
                getIllnesses());
    }

    @Override
    public String toString() {
        return "Patient{" + "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", dateOfBirth=" + dateOfBirth +
                ", illnesses=" + illnesses + '}';
    }
}
