package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class PatientResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("phone_number")
    private Integer phoneNumber;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("identification_number")
    private String identificationNumber;

    @JsonProperty("create_date")
    private String createDate;

    @JsonProperty("update_date")
    private String updateDate;

    @JsonProperty("illnesses")
    private Set<IllnessResponseDto> illnesses;

    public PatientResponseDto() {
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Set<IllnessResponseDto> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<IllnessResponseDto> illnesses) {
        this.illnesses = illnesses;
    }
}
