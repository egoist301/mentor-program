package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class DoctorResponseDto {
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

    @JsonProperty("price_per_consultation")
    private BigDecimal pricePerConsultation;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("identification_number")
    private String identificationNumber;

    @JsonProperty("create_date")
    private LocalDate createDate;

    @JsonProperty("update_date")
    private LocalDate updateDate;

    @JsonProperty("illnesses")
    private Set<IllnessResponseDto> illnesses;

    public DoctorResponseDto() {
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

    public Set<IllnessResponseDto> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<IllnessResponseDto> illnesses) {
        this.illnesses = illnesses;
    }

    public BigDecimal getPricePerConsultation() {
        return pricePerConsultation;
    }

    public void setPricePerConsultation(BigDecimal pricePerConsultation) {
        this.pricePerConsultation = pricePerConsultation;
    }
}
