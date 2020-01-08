package com.epam.esm.repository;

import com.epam.esm.repository.entity.Patient;
import com.epam.esm.repository.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PatientDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Patient get(final Long id) {
        final String SELECT_PATIENT_BY_ID =
                "SELECT id, first_name, last_name, middle_name, phone_number, date_of_birth FROM patients WHERE id = ?";
        return jdbcTemplate.queryForObject(SELECT_PATIENT_BY_ID, new PatientMapper(), id);
    }

    public void create(final Patient entity) {
        final String INSERT_PATIENT =
                "INSERT INTO patients(first_name, last_name, middle_name, phone_number, date_of_birth)"
                        + " VALUES(?,?,?,?,?)";
        jdbcTemplate.update(INSERT_PATIENT, entity.getFirstName(), entity.getLastName(), entity.getMiddleName(),
                entity.getPhoneNumber(), entity.getDateOfBirth());
    }

    public void update(final Patient entity) {
        final String UPDATE_PATIENT =
                "UPDATE patients SET first_name = ?, last_name = ?, middle_name = ?, phone_number = ?, "
                        + "date_of_birth = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_PATIENT, entity.getFirstName(), entity.getLastName(), entity.getMiddleName(),
                entity.getPhoneNumber(), entity.getDateOfBirth(), entity.getId());
    }

    public void delete(final Long id) {
        final String DELETE_PATIENT = "DELETE FROM patients WHERE id = ?";
        jdbcTemplate.update(DELETE_PATIENT, id);
    }
}
