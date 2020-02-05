package com.epam.esm.dto.doctor;

import com.epam.esm.dto.illness.IllnessRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

public class DoctorRequestDto {
    @NotBlank(message = "first name can't be null or empty")
    @Size(min = 2, max = 16, message = "first name can be 2 to 16 characters long")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "last name can't be null or empty")
    @Size(min = 2, max = 16, message = "last name can be 2 to 16 characters long")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "middle name can't be null or empty")
    @Size(min = 4, max = 16, message = "middle name can be 4 to 16 characters long")
    @JsonProperty("middle_name")
    private String middleName;

    @NotNull(message = "phone number can't be null")
    @Digits(integer = 7, fraction = 0, message = "7 digits")
    @JsonProperty("phone_number")
    private BigInteger phoneNumber;

    @NotNull(message = "price per consultation can't be null")
    @Positive(message = "price only positive")
    @Digits(integer = 11, fraction = 2, message = "11 digits")
    @JsonProperty("price_per_consultation")
    private BigDecimal pricePerConsultation;

    @NotNull(message = "date of birth can't be null")
    @PastOrPresent(message = "date can be past or present")
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

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

    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<IllnessRequestDto> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<IllnessRequestDto> illnesses) {
        this.illnesses = illnesses;
    }

    public BigDecimal getPricePerConsultation() {
        return pricePerConsultation;
    }

    public void setPricePerConsultation(BigDecimal pricePerConsultation) {
        this.pricePerConsultation = pricePerConsultation;
    }
}
