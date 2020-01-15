package com.epam.esm.repository;

import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.mapper.IllnessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class IllnessDao {
    private static final String COLUMNS_OF_ILLNESS = "name, name_in_latin, chance_to_die";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IllnessDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Illness get(Long id) {
        final String SELECT_ILLNESS_BY_ID = "SELECT id, " + COLUMNS_OF_ILLNESS + " FROM illness WHERE id = ?";
        return jdbcTemplate.queryForObject(SELECT_ILLNESS_BY_ID, new IllnessMapper(), id);
    }

    public Long getID(String name) {
        final String SELECT_ILLNESS_BY_NAME = "SELECT id FROM illness WHERE name = ?";
        return jdbcTemplate.queryForObject(SELECT_ILLNESS_BY_NAME, Long.class, name);
    }


    public boolean isIllnessExistByName(String name) {
        final String SELECT_COUNT_BY_NAME = "SELECT COUNT(*) FROM illness WHERE name = ?";
        return jdbcTemplate.queryForObject(SELECT_COUNT_BY_NAME, Integer.class, name) > 0;
    }

    public void create(Illness illness) {
        final String INSERT_ILLNESS = "INSERT INTO illness(" + COLUMNS_OF_ILLNESS + ") VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_ILLNESS, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, illness.getName());
            preparedStatement.setString(2, illness.getNameInLatin());
            preparedStatement.setInt(3, illness.getChanceToDie());
            return preparedStatement;
        }, keyHolder);
        illness.setId(((Integer) keyHolder.getKeys().get("id")).longValue());
    }

    public void delete(Long id) {
        final String DELETE_ILLNESS = "DELETE FROM illness WHERE id = ?";
        jdbcTemplate.update(DELETE_ILLNESS, id);
    }

    public List<Illness> getAll() {
        final String SEARCH = "SELECT * FROM illness";
        return jdbcTemplate.query(SEARCH, new IllnessMapper());
    }

    public List<Illness> findByPatientId(Long patientId) {
        final String FIND_BY_PATIENT_ID =
                "SELECT i.* FROM illness i JOIN patients_illness pi ON i.id = pi.illness_id WHERE patient_id = ?";
        return jdbcTemplate.query(FIND_BY_PATIENT_ID, new IllnessMapper(), patientId);
    }

    public void update(Illness illness) {
        final String UPDATE = "UPDATE illness SET name = ?, name_in_latin = ?, chance_to_die = ?";
        jdbcTemplate.update(UPDATE, illness.getName(), illness.getNameInLatin(), illness.getChanceToDie());
    }

    public void partialUpdate(Illness illness) {
        String PARTIAL_UPDATE = "UPDATE illness SET ";
        if (illness.getName() != null) {
            PARTIAL_UPDATE = PARTIAL_UPDATE.concat("name = '" + illness.getName() + "'");
            if (illness.getNameInLatin() != null) {
                PARTIAL_UPDATE = PARTIAL_UPDATE.concat(", name_in_latin = '" + illness.getNameInLatin() + "'");
            }
            PARTIAL_UPDATE = getChanceToDie(illness, PARTIAL_UPDATE, ", chance_to_die = ");
        } else if (illness.getNameInLatin() != null) {
            PARTIAL_UPDATE = PARTIAL_UPDATE.concat("name_in_latin = '" + illness.getNameInLatin() + "'");
            PARTIAL_UPDATE = getChanceToDie(illness, PARTIAL_UPDATE, ", chance_to_die = ");
        } else {
            PARTIAL_UPDATE = getChanceToDie(illness, PARTIAL_UPDATE, "chance_to_die = ");
        }
        PARTIAL_UPDATE = PARTIAL_UPDATE.concat(" WHERE id = " + illness.getId());
        jdbcTemplate.update(PARTIAL_UPDATE);
    }

    private String getChanceToDie(Illness illness, String PARTIAL_UPDATE, String s) {
        if (illness.getChanceToDie() != null) {
            PARTIAL_UPDATE = PARTIAL_UPDATE.concat(s + illness.getChanceToDie());
        }
        return PARTIAL_UPDATE;
    }
}
