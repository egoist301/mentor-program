package com.epam.esm.controller.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PatientDto {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private int phoneNumber;
    private String date;

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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirthConverted(String timezone) throws ParseException {
        SIMPLE_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timezone));
        return SIMPLE_DATE_FORMAT.parse(this.date);
    }

    public void setDateOfBirth(Date date, String timezone) {
        SIMPLE_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timezone));
        this.date = SIMPLE_DATE_FORMAT.format(date);
    }
}
