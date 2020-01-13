package com.epam.esm.repository;

import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.mapper.IllnessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;

@Repository
public class IllnessDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IllnessDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Illness get(Long id) {
        final String SELECT_ILLNESS_BY_ID = "SELECT id, name, name_in_latin, chance_to_die FROM illness WHERE id = ?";
        return jdbcTemplate.queryForObject(SELECT_ILLNESS_BY_ID, new IllnessMapper(), id);
    }

    public void create(Illness entity) {
        final String INSERT_ILLNESS = "INSERT INTO illness(name, name_in_latin, chance_to_die) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ILLNESS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getNameInLatin());
            preparedStatement.setInt(3, entity.getChanceToDie());
            return preparedStatement;
        }, keyHolder);
        entity.setId((long) keyHolder.getKey());
    }

    public void delete(Long id) {
        final String DELETE_ILLNESS = "DELETE FROM illness WHERE id = ?";
        jdbcTemplate.update(DELETE_ILLNESS, id);
    }

    public List<Illness> get(String name, String nameInLatin) {
        final String SEARCH = "SELECT * FROM searchIllness(?,?)";
        return jdbcTemplate.query(SEARCH, new IllnessMapper(), name + '%', nameInLatin + '%');
    }

    public List<Illness> findByPatientId(Long patient) {
        final String FIND_BY_PATIENT_ID =
                "SELECT i.* FROM illness i JOIN patients_illness pi ON i.id = pi.illness_id WHERE patient_id = ?";
        return jdbcTemplate.query(FIND_BY_PATIENT_ID, new IllnessMapper(), patient);
    }
}
