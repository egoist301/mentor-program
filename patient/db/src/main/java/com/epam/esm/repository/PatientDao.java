package com.epam.esm.repository;

import com.epam.esm.repository.entity.Patient;
import com.epam.esm.repository.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
            ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + MIDDLE_NAME + ", " + PHONE_NUMBER + ", " +
                    DATE_OF_BIRTH + ", " + IDENTIFICATION_NUMBER + ", " + CREATE_DATE + ", " + UPDATE_DATE;
    private static final String TABLE_NAME = "patient";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PatientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Patient> findById(Long id) {
        final String QUERY = "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        return jdbcTemplate.query(QUERY, new PatientMapper(), id);
    }

    public List<Patient> findByIdentificationNumber(String identificationNumber) {
        final String QUERY =
                "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + IDENTIFICATION_NUMBER + " = ?";
        return jdbcTemplate.query(QUERY, new PatientMapper(), identificationNumber);
    }

    public void create(Patient patient) {
        final String QUERY =
                "INSERT INTO " + TABLE_NAME + "(" + FIRST_NAME + ", " + LAST_NAME + ", " + MIDDLE_NAME + ", " +
                        PHONE_NUMBER + ", " + DATE_OF_BIRTH + ", " + IDENTIFICATION_NUMBER + ", " + CREATE_DATE + ", " +
                        UPDATE_DATE + ") VALUES (?,?,?,?,?,?,current_date,current_date)";
        jdbcTemplate.update(QUERY, patient.getFirstName(), patient.getLastName(), patient.getMiddleName(),
                patient.getPhoneNumber(), patient.getDateOfBirth(), patient.getIdentificationNumber());
    }

    public void delete(Long id) {
        final String QUERY = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        jdbcTemplate.update(QUERY, id);
    }

    public void update(Patient patient) {
        final String QUERY =
                "UPDATE " + TABLE_NAME + " SET " + FIRST_NAME + " = ?, " + LAST_NAME + " = ?, " + MIDDLE_NAME +
                        " = ?, " + PHONE_NUMBER + " = ?, " + DATE_OF_BIRTH + " = ?, " + IDENTIFICATION_NUMBER +
                        " = ?, " + UPDATE_DATE + " = current_date WHERE " + ID + " = ?";
        jdbcTemplate.update(QUERY, patient.getFirstName(), patient.getLastName(), patient.getMiddleName(),
                patient.getPhoneNumber(), patient.getDateOfBirth(), patient.getIdentificationNumber(), patient.getId());
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

        Date dateOfBirth = patient.getDateOfBirth();
        if (dateOfBirth != null) {
            setPart = addCommaAndSpace(setPart, fieldCount);
            setPart = setPart.concat(DATE_OF_BIRTH + " = '" + dateOfBirth + "'");
            fieldCount++;
        }

        String identificationNumber = patient.getIdentificationNumber();
        if (identificationNumber != null) {
            setPart = addCommaAndSpace(setPart, fieldCount);
            setPart = setPart.concat(IDENTIFICATION_NUMBER + " = '" + identificationNumber + "'");
            fieldCount++;
        }

        setPart = addCommaAndSpace(setPart, fieldCount);
        setPart = setPart.concat(UPDATE_DATE + " = current_date");

        jdbcTemplate.update(updatePart + setPart + wherePart);
    }

    private String addCommaAndSpace(String query, int fieldCount) {
        if (fieldCount != 0) {
            query = query.concat(", ");
        }
        return query;
    }


    public List<Patient> getAll(String searchByFirstName, String searchByLastName, String searchByMiddleName,
                                String searchByIllnessName, String sortBy, String order) {
        return jdbcTemplate.query(getQuery(sortBy, order), new PatientMapper(), searchByFirstName, searchByLastName,
                searchByMiddleName, searchByIllnessName);
    }

    private String getQuery(String sortBy, String order) {
        String search = "SELECT " + ALL_FIELDS + " FROM searchPatient(?,?,?,?)";
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
        final String QUERY = "INSERT INTO patient_illness(patient_id, illness_id) VALUES (?,?)";
        jdbcTemplate.update(QUERY, patientId, illnessId);
    }

    public void removeRefPatientIllness(Long patientId, Long illnessId) {
        final String QUERY = "DELETE FROM patient_illness WHERE patient_id = ? AND illness_id = ?";
        jdbcTemplate.update(QUERY, patientId, illnessId);
    }

    public List<Patient> findByIdentificationNumberWithDifferentId(Long id, String identificationNumber) {
        final String QUERY =
                "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + IDENTIFICATION_NUMBER + " = ? AND " + ID +
                        " <> ?";
        return jdbcTemplate.query(QUERY, new PatientMapper(), identificationNumber, id);
    }
}
