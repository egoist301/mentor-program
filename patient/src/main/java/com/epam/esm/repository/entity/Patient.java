package com.epam.esm.repository.entity;

import java.util.Date;
import java.util.Objects;

public class Patient extends Entity {
    private String firstName;
    private String lastName;
    private String middleName;
    private int phoneNumber;
    private Date dateOfBirth;

    public Patient() {
    }

    public Patient(final Long idNew, final String firstName, final String lastName, final String middleName,
                   final int phoneNumber, final Date dateOfBirth) {
        super(idNew);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
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

    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Patient patient = (Patient) o;
        return getPhoneNumber() == patient.getPhoneNumber() &&
                Objects.equals(getFirstName(), patient.getFirstName()) &&
                Objects.equals(getLastName(), patient.getLastName()) &&
                Objects.equals(getMiddleName(), patient.getMiddleName()) &&
                Objects.equals(getDateOfBirth(), patient.getDateOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(super.hashCode(), getFirstName(), getLastName(), getMiddleName(), getPhoneNumber(),
                        getDateOfBirth());
    }

    @Override
    public String toString() {
        return "Patient{" + super.toString() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
