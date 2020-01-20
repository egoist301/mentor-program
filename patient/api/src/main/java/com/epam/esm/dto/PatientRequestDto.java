package com.epam.esm.dto;

import com.epam.esm.converter.LocalDateDeserializer;
import com.epam.esm.converter.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class PatientRequestDto {
    @NotNull(message = "first name can't be null")
    @Size(min = 2, max = 16, message = "first name can be 2 to 16 characters long")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "last name can't be null")
    @Size(min = 2, max = 16, message = "last name can be 2 to 16 characters long")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "middle name can't be null")
    @Size(min = 4, max = 16, message = "middle name can be 4 to 16 characters long")
    @JsonProperty("middle_name")
    private String middleName;

    @NotNull(message = "phone number can't be null")
    @Min(value = 1000000, message = "phone number must be 7 digits long")
    @Max(value = 9999999, message = "phone number must be 7 digits long")
    @JsonProperty("phone_number")
    private Integer phoneNumber;

    @NotNull(message = "date of birth can't be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @PastOrPresent(message = "date can be past or present")
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "identification number can't be null")
    @Size(min = 14, max = 14, message = "identification number must be 14 characters long")
    @JsonProperty("identification_number")
    private String identificationNumber;

    @Valid
    @JsonProperty("illnesses")
    private Set<IllnessRequestDto> illnesses;

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

    public Set<IllnessRequestDto> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<IllnessRequestDto> illnesses) {
        this.illnesses = illnesses;
    }
}
