package com.epam.esm.repository;

import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.mapper.IllnessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IllnessDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IllnessDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Illness get(final Long id) {
        final String SELECT_ILLNESS_BY_ID = "SELECT id, name, name_in_latin, chance_to_die FROM illness WHERE id = ?";
        return jdbcTemplate.queryForObject(SELECT_ILLNESS_BY_ID, new IllnessMapper(), id);
    }

    public void create(final Illness entity) {
        final String INSERT_ILLNESS = "INSERT INTO illness(name, name_in_latin, chance_to_die) VALUES (?,?,?)";
        jdbcTemplate.update(INSERT_ILLNESS, entity.getName(), entity.getNameInLatin(), entity.getChanceToDie());
    }

    public void delete(final Long id) {
        final String DELETE_ILLNESS = "DELETE FROM illness WHERE id = ?";
        jdbcTemplate.update(DELETE_ILLNESS, id);
    }

    public List<Illness> search(String name, String nameInLatin) {
        final String SEARCH = "SELECT * FROM searchIllness(?,?)";
        return jdbcTemplate.query(SEARCH, new IllnessMapper(), name + '%', nameInLatin + '%');
    }
}
