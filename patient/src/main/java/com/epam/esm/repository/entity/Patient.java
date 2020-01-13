package com.epam.esm.repository.entity;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Component
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private int phoneNumber;
    private Date dateOfBirth;
    private Set<Illness> illnesses;

    public Patient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<Illness> illnessesNew) {
        illnesses = illnessesNew;
    }

    @Override
    public boolean equals(Object oNew) {
        if (this == oNew) return true;
        if (!(oNew instanceof Patient)) return false;
        Patient patient = (Patient) oNew;
        return getPhoneNumber() == patient.getPhoneNumber() &&
                Objects.equals(getId(), patient.getId()) &&
                Objects.equals(getFirstName(), patient.getFirstName()) &&
                Objects.equals(getLastName(), patient.getLastName()) &&
                Objects.equals(getMiddleName(), patient.getMiddleName()) &&
                Objects.equals(getDateOfBirth(), patient.getDateOfBirth()) &&
                Objects.equals(getIllnesses(), patient.getIllnesses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getMiddleName(), getPhoneNumber(), getDateOfBirth(), getIllnesses());
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
