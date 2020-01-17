package com.epam.esm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

public class PatientPartialRequestDto {
    @Size(min = 2, max = 16)
    @JsonProperty("first_name")
    private String firstName;

    @Size(min = 2, max = 16)
    @JsonProperty("last_name")
    private String lastName;

    @Size(min = 4, max = 16)
    @JsonProperty("middle_name")
    private String middleName;

    @Min(1000000)
    @Max(9999999)
    @JsonProperty("phone_number")
    private Integer phoneNumber;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @Size(min = 14, max = 14)
    @JsonProperty("identification_number")
    private String identificationNumber;

    @Valid
    @JsonProperty("illnesses")
    private Set<IllnessPartialRequestDto> illnesses;

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Set<IllnessPartialRequestDto> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<IllnessPartialRequestDto> illnesses) {
        this.illnesses = illnesses;
    }
}
