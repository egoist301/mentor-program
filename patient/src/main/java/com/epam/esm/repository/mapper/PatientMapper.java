package com.epam.esm.repository.mapper;

import com.epam.esm.repository.entity.Patient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientMapper implements RowMapper<Patient> {
    @Override
    public Patient mapRow(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong(1));
        patient.setFirstName(resultSet.getString(2));
        patient.setLastName(resultSet.getString(3));
        patient.setMiddleName(resultSet.getString(4));
        patient.setPhoneNumber(resultSet.getInt(5));
        patient.setDateOfBirth(resultSet.getDate(6));
        return patient;
    }
}
