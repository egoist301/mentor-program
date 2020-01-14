package com.epam.esm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientDto {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Min(1)
    private Long id;
    @JsonProperty("first_name")
    @NotNull
    @Size(min = 2, max = 16)
    private String firstName;
    @JsonProperty("last_name")
    @NotNull
    @Size(min = 2, max = 16)
    private String lastName;
    @JsonProperty("middle_name")
    @Size(min = 4, max = 16)
    @NotNull
    private String middleName;
    @JsonProperty("phone_number")
    @Min(1000000)
    @Max(9999999)
    @NotNull
    private Integer phoneNumber;
    @NotNull
    @PastOrPresent
    private String date;
    private List<IllnessDto> illnesses;

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

    public List<IllnessDto> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<IllnessDto> illnesses) {
        this.illnesses = illnesses;
    }
}
