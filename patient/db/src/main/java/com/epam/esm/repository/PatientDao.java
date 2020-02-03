package com.epam.esm.repository;

import com.epam.esm.repository.entity.Patient;
import com.epam.esm.repository.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PatientDao {
    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String DATE_OF_BIRTH = "date_of_birth";
    private static final String IDENTIFICATION_NUMBER = "identification_number";
    private static final String CREATE_DATE = "create_date";
    private static final String UPDATE_DATE = "update_date";
    private static final String ALL_FIELDS =
            "id, first_name, last_name, middle_name, phone_number, date_of_birth, identification_number, create_date, update_date";
    private static final String TABLE_NAME = "patient";
    private static final String FIND_BY_ID = "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
    private static final String FIND_BY_IDENTIFICATION_NUMBER =
            "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + IDENTIFICATION_NUMBER + " = ?";
    private static final String INSERT =
            "INSERT INTO " + TABLE_NAME + "(" + FIRST_NAME + ", " + LAST_NAME + ", " + MIDDLE_NAME + ", " +
                    PHONE_NUMBER + ", " + DATE_OF_BIRTH + ", " + IDENTIFICATION_NUMBER + ", " + CREATE_DATE + ", " +
                    UPDATE_DATE + ") VALUES (?,?,?,?,?,?,current_date,current_date)";
    private static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
    private static final String UPDATE =
            "UPDATE " + TABLE_NAME + " SET " + FIRST_NAME + " = ?, " + LAST_NAME + " = ?, " + MIDDLE_NAME +
                    " = ?, " + PHONE_NUMBER + " = ?, " + DATE_OF_BIRTH + " = ?, " + UPDATE_DATE
                    + " = current_date WHERE " + ID + " = ?";
    private static final String SAVE_REF = "INSERT INTO patient_illness(patient_id, illness_id) VALUES (?,?)";
    private static final String REMOVE_REF = "DELETE FROM patient_illness WHERE patient_id = ? AND illness_id = ?";
    private static final String CHECK_REF = "SELECT id FROM patient_illness WHERE patient_id = ? AND illness_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PatientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Patient> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID, new PatientMapper(), id);
    }

    public List<Patient> findByIdentificationNumber(String identificationNumber) {
        return jdbcTemplate.query(FIND_BY_IDENTIFICATION_NUMBER, new PatientMapper(), identificationNumber);
    }

    public void create(Patient patient) {
        jdbcTemplate.update(INSERT, patient.getFirstName(), patient.getLastName(), patient.getMiddleName(),
                patient.getPhoneNumber(), patient.getDateOfBirth(), patient.getIdentificationNumber());
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    public void update(Patient patient) {
        jdbcTemplate.update(UPDATE, patient.getFirstName(), patient.getLastName(), patient.getMiddleName(),
                patient.getPhoneNumber(), patient.getDateOfBirth(), patient.getId());
    }

    public void partialUpdate(Patient patient) {
        String updatePart = "UPDATE " + TABLE_NAME;
        String setPart = " SET ";
        String wherePart = " WHERE " + ID + " = " + patient.getId();
        int fieldCount = 0;

        String firstName = patient.getFirstName();
        if (firstName != null) {
            setPart = setPart.concat(FIRST_NAME + " = '" + firstName + "'");
            fieldCount++;
        }

        String lastName = patient.getLastName();
        if (lastName != null) {
            setPart = addCommaAndSpace(setPart, fieldCount);
            setPart = setPart.concat(LAST_NAME + " = '" + lastName + "'");
            fieldCount++;
        }

        String middleName = patient.getMiddleName();
        if (middleName != null) {
            setPart = addCommaAndSpace(setPart, fieldCount);
            setPart = setPart.concat(MIDDLE_NAME + " = '" + middleName + "'");
            fieldCount++;
        }

        Integer phoneNumber = patient.getPhoneNumber();
        if (phoneNumber != null) {
            setPart = addCommaAndSpace(setPart, fieldCount);
            setPart = setPart.concat(PHONE_NUMBER + " = " + phoneNumber);
            fieldCount++;
        }

        LocalDate dateOfBirth = patient.getDateOfBirth();
        if (dateOfBirth != null) {
            setPart = addCommaAndSpace(setPart, fieldCount);
            setPart = setPart.concat(DATE_OF_BIRTH + " = '" + dateOfBirth + "'");
            fieldCount++;
        }

        setPart = addCommaAndSpace(setPart, fieldCount);
        setPart = setPart.concat(UPDATE_DATE + " = current_date");

        jdbcTemplate.update(updatePart + setPart + wherePart);
    }

    private String addCommaAndSpace(String query, int fieldCount) {
        return fieldCount != 0 ? query.concat(", ") : query;
    }


    public List<Patient> getAll(List<String> filters, String sortBy, String order) {
        return jdbcTemplate.query(getQuery(sortBy, order), new PatientMapper(), filters.toArray());
    }

    private String getQuery(String sortBy, String order) {
        String search = new StringBuilder().append("SELECT ").append(ALL_FIELDS).append(" FROM searchPatient(?,?,?,?)")
                .toString();
        if (sortBy != null && (sortBy.equals(FIRST_NAME) || sortBy.equals(LAST_NAME) || sortBy.equals(MIDDLE_NAME) ||
                sortBy.equals(DATE_OF_BIRTH))) {
            search = search.concat(" ORDER BY ".concat(sortBy));

            if (order != null && (order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc"))) {
                search = search.concat(" ".concat(order));
            }
        }
        return search;
    }

    public void saveRefPatientIllness(Long patientId, Long illnessId) {
        jdbcTemplate.update(SAVE_REF, patientId, illnessId);
    }

    public void removeRefPatientIllness(Long patientId, Long illnessId) {
        jdbcTemplate.update(REMOVE_REF, patientId, illnessId);
    }

    public List<Integer> isRefPatientIllnessExist(Long patientId, Long illnessId) {
        return jdbcTemplate.query(CHECK_REF, (resultSet, i) -> resultSet.getInt(1), patientId, illnessId);
    }
}
