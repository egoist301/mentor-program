package com.epam.esm.repository;

import com.epam.esm.repository.entity.Patient;
import com.epam.esm.repository.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PatientDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PatientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Patient get(Long id) {
        final String SELECT_PATIENT_BY_ID =
                "SELECT id, first_name, last_name, middle_name, phone_number, date_of_birth FROM patients WHERE id = ?";
        return jdbcTemplate.queryForObject(SELECT_PATIENT_BY_ID, new PatientMapper(), id);
    }

    public void create(Patient entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String INSERT_PATIENT =
                "INSERT INTO patients(first_name, last_name, middle_name, phone_number, date_of_birth)"
                        + " VALUES(?,?,?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getMiddleName());
            preparedStatement.setInt(4, entity.getPhoneNumber());
            preparedStatement.setDate(5, new Date(entity.getDateOfBirth().getTime()));
            return preparedStatement;
        }, keyHolder);
        entity.setId(((Integer) keyHolder.getKeys().get("id")).longValue());
    }

    public void update(Patient entity) {
        final String UPDATE_PATIENT =
                "UPDATE patients SET first_name = ?, last_name = ?, middle_name = ?, phone_number = ?, "
                        + "date_of_birth = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_PATIENT, entity.getFirstName(), entity.getLastName(), entity.getMiddleName(),
                entity.getPhoneNumber(), new Date(entity.getDateOfBirth().getTime()), entity.getId());
    }

    public void delete(Long id) {
        final String DELETE_PATIENT = "DELETE FROM patients WHERE id = ?";
        jdbcTemplate.update(DELETE_PATIENT, id);
    }

    public List<Patient> getAll(String searchByFirstName, String searchByLastName, String searchByMiddleName) {
        final String SEARCH = "SELECT * FROM searchPatient(?,?,?)";
        return jdbcTemplate.query(SEARCH, new PatientMapper(), searchByFirstName, searchByLastName, searchByMiddleName);
    }

    public void saveIllness(Long patient, Long illness) {
        final String SAVE = "INSERT INTO patients_illness(patient_id, illness_id) VALUES (?,?)";
        jdbcTemplate.update(SAVE, patient, illness);
    }

    public void removeIllness(Long patient, Long illness) {
        final String DELETE = "DELETE FROM patients_illness WHERE patient_id = ? AND illness_id = ?";
        jdbcTemplate.update(DELETE, patient, illness);
    }
}
