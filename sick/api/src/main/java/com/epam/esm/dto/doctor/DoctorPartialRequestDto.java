package com.epam.esm.dto.doctor;

import com.epam.esm.dto.NullableNotBlank;
import com.epam.esm.dto.illness.IllnessPartialRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class DoctorPartialRequestDto {
    @NullableNotBlank
    @Size(min = 2, max = 16, message = "first name can be 2 to 16 characters long")
    @JsonProperty("first_name")
    private String firstName;

    @NullableNotBlank
    @Size(min = 2, max = 16, message = "last name can be 2 to 16 characters long")
    @JsonProperty("last_name")
    private String lastName;

    @NullableNotBlank
    @Size(min = 4, max = 16, message = "middle name can be 4 to 16 characters long")
    @JsonProperty("middle_name")
    private String middleName;

    @Min(value = 1000000, message = "phone number must be 7 digits long")
    @Max(value = 9999999, message = "phone number must be 7 digits long")
    @JsonProperty("phone_number")
    private Integer phoneNumber;

    @Positive(message = "price only positive")
    @Digits(integer = 11, fraction = 2, message = "11 digits")
    @JsonProperty("price_per_consultation")
    private BigDecimal pricePerConsultation;

    @PastOrPresent(message = "date can be past or present")
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<IllnessPartialRequestDto> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<IllnessPartialRequestDto> illnesses) {
        this.illnesses = illnesses;
    }

    public BigDecimal getPricePerConsultation() {
        return pricePerConsultation;
    }

    public void setPricePerConsultation(BigDecimal pricePerConsultation) {
        this.pricePerConsultation = pricePerConsultation;
    }
}
