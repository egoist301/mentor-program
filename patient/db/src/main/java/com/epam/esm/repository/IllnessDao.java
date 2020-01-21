package com.epam.esm.repository;

import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.mapper.IllnessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public class IllnessDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CHANCE_TO_DIE = "chance_to_die";
    private static final String CREATE_DATE = "create_date";
    private static final String UPDATE_DATE = "update_date";
    private static final String ALL_FIELDS =
            ID + ", " + NAME + ", " + DESCRIPTION + ", " + CHANCE_TO_DIE + ", " + CREATE_DATE + ", " + UPDATE_DATE;
    private static final String TABLE_NAME = "illness";
    private static final String FIND_BY_ID = "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
    private static final String FIND_BY_NAME =
            "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + NAME + " = ?";
    private static final String FIND_BY_NAME_WITH_DIFFERENT_ID =
            "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE " + NAME + " = ? AND " + ID + " <> ?";
    private static final String GET_ALL = "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME;
    private static final String INSERT =
            "INSERT INTO " + TABLE_NAME + "(" + NAME + ", " + DESCRIPTION + ", " + CHANCE_TO_DIE + ", " +
                    CREATE_DATE + ", " + UPDATE_DATE + ") VALUES (?,?,?,current_date,current_date)";
    private static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
    private static final String UPDATE =
            "UPDATE " + TABLE_NAME + " SET " + NAME + " = ?, " + DESCRIPTION + " = ?, " + CHANCE_TO_DIE + " = ?, " +
                    UPDATE_DATE + " = current_date WHERE " + ID + " = ?";
    private static final String FIND_BY_PATIENT_ID =
            "SELECT i.* FROM illness i JOIN patient_illness pi ON i.id = pi.illness_id WHERE patient_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IllnessDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Illness findById(Long id) {
        List<Illness> illnesses = jdbcTemplate.query(FIND_BY_ID, new IllnessMapper(), id);

        if (illnesses.isEmpty()) {
            return null;
        } else {
            return illnesses.get(0);
        }
    }

    public Illness findByName(String name) {
        List<Illness> illnesses = jdbcTemplate.query(FIND_BY_NAME, new IllnessMapper(), name);

        if (illnesses.isEmpty()) {
            return null;
        } else {
            return illnesses.get(0);
        }
    }

    public Illness findByNameWithDifferentId(String name, Long id) {
        List<Illness> illnesses = jdbcTemplate.query(FIND_BY_NAME_WITH_DIFFERENT_ID, new IllnessMapper(), name, id);
        if (illnesses.isEmpty()) {
            return null;
        } else {
            return illnesses.get(0);
        }
    }

    public List<Illness> getAll() {
        return jdbcTemplate.query(GET_ALL, new IllnessMapper());
    }

    public void create(Illness illness) {
        jdbcTemplate.update(INSERT, illness.getName(), illness.getDescription(), illness.getChanceToDie());
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    public void update(Illness illness) {
        jdbcTemplate
                .update(UPDATE, illness.getName(), illness.getDescription(), illness.getChanceToDie(), illness.getId());
    }

    public void partialUpdate(Illness illness) {
        String updatePart = "UPDATE " + TABLE_NAME;
        String setPart = " SET ";
        String wherePart = " WHERE " + ID + " = " + illness.getId();
        int fieldCount = 0;

        if (illness.getName() != null) {
            setPart = setPart.concat(NAME + " = '" + illness.getName() + "'");
            fieldCount++;
        }
        if (illness.getDescription() != null) {
            setPart = addCommaAndSpace(setPart, fieldCount);
            setPart = setPart.concat(DESCRIPTION + " = '" + illness.getDescription() + "'");
            fieldCount++;
        }
        if (illness.getChanceToDie() != null) {
            setPart = addCommaAndSpace(setPart, fieldCount);
            setPart = setPart.concat(CHANCE_TO_DIE + " = " + illness.getChanceToDie());
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

    public Set<Illness> findByPatientId(Long patientId) {
        return new LinkedHashSet<>(jdbcTemplate.query(FIND_BY_PATIENT_ID, new IllnessMapper(), patientId));
    }
}
