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
            ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + MIDDLE_NAME + ", " + PHONE_NUMBER + ", " +
                    DATE_OF_BIRTH + ", " + IDENTIFICATION_NUMBER + ", " + CREATE_DATE + ", " + UPDATE_DATE;
    private static final String TABLE_NAME = "patient";

    private final JdbcTemplate jdbcTemplate;
    public static final String FIND_BY_ID = "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
    public static final String FIND_BY_IDENTIFICATION_NUMBER =
            "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + IDENTIFICATION_NUMBER + " = ?";
    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME + "(" + FIRST_NAME + ", " + LAST_NAME + ", " + MIDDLE_NAME + ", " +
                    PHONE_NUMBER + ", " + DATE_OF_BIRTH + ", " + IDENTIFICATION_NUMBER + ", " + CREATE_DATE + ", " +
                    UPDATE_DATE + ") VALUES (?,?,?,?,?,?,current_date,current_date)";
    public static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
    public static final String UPDATE =
            "UPDATE " + TABLE_NAME + " SET " + FIRST_NAME + " = ?, " + LAST_NAME + " = ?, " + MIDDLE_NAME +
                    " = ?, " + PHONE_NUMBER + " = ?, " + DATE_OF_BIRTH + " = ?, " + IDENTIFICATION_NUMBER +
                    " = ?, " + UPDATE_DATE + " = current_date WHERE " + ID + " = ?";
    public static final String SAVE_REF = "INSERT INTO patient_illness(patient_id, illness_id) VALUES (?,?)";
    public static final String REMOVE_REF = "DELETE FROM patient_illness WHERE patient_id = ? AND illness_id = ?";
    public static final String FIND_BY_IDENTIFICATION_NUMBER_WITH_DIFFERENT_ID =
            "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + IDENTIFICATION_NUMBER + " = ? AND " + ID +
                    " <> ?";
    public static final String CHECK_REF = "SELECT id FROM patient_illness WHERE patient_id = ? AND illness_id = ?";

    @Autowired
    public PatientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Patient findById(Long id) {
        List<Patient> patients = jdbcTemplate.query(FIND_BY_ID, new PatientMapper(), id);

        if (patients.isEmpty()) {
            return null;
        } else {
            return patients.get(0);
        }
    }

    public Patient findByIdentificationNumber(String identificationNumber) {
        List<Patient> patients =
                jdbcTemplate.query(FIND_BY_IDENTIFICATION_NUMBER, new PatientMapper(), identificationNumber);

        if (patients.isEmpty()) {
            return null;
        } else {
            return patients.get(0);
        }
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

        LocalDate dateOfBirth = patient.getDateOfBirth();
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
        jdbcTemplate.update(SAVE_REF, patientId, illnessId);
    }

    public void removeRefPatientIllness(Long patientId, Long illnessId) {
        jdbcTemplate.update(REMOVE_REF, patientId, illnessId);
    }

    public Patient findByIdentificationNumberWithDifferentId(Long id, String identificationNumber) {
        List<Patient> patients = jdbcTemplate
                .query(FIND_BY_IDENTIFICATION_NUMBER_WITH_DIFFERENT_ID, new PatientMapper(), identificationNumber, id);

        if (patients.isEmpty()) {
            return null;
        } else {
            return patients.get(0);
        }
    }

    public Integer isRefPatientIllnessExist(Long patientId, Long illnessId) {
        List<Integer> integers = jdbcTemplate.query(CHECK_REF, (resultSet, i) -> resultSet.getInt(1), patientId, illnessId);

        if (integers.isEmpty()) {
            return null;
        } else {
            return integers.get(0);
        }
    }
}
