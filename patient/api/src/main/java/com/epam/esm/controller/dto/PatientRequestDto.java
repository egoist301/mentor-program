package com.epam.esm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientRequestDto {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
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
    @PastOrPresent
    @JsonProperty("date_of_birth")
    private String date;
    @JsonProperty("illnesses")
    private List<IllnessResponseDto> illnesses;

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

    public Date getDateOfBirthConverted() throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(this.date);
    }

    public void setDateOfBirth(Date date) {
        this.date = SIMPLE_DATE_FORMAT.format(date);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<IllnessResponseDto> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<IllnessResponseDto> illnesses) {
        this.illnesses = illnesses;
    }
}
