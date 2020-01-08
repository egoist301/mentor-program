package com.epam.esm.repository.impl;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.mapper.IllnessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IllnessDaoImpl implements IllnessDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IllnessDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Illness get(final Long id) {
        final String SELECT_ILLNESS_BY_ID = "SELECT id, name, name_in_latin, chance_to_die FROM illness WHERE id = ?";
        return jdbcTemplate.queryForObject(SELECT_ILLNESS_BY_ID, new IllnessMapper(), id);
    }

    @Override
    public void create(final Illness entity) {
        final String INSERT_ILLNESS = "INSERT INTO illness(name, name_in_latin, chance_to_die) VALUES (?,?,?)";
        jdbcTemplate.update(INSERT_ILLNESS, entity.getName(), entity.getNameInLatin(), entity.getChanceToDie());
    }

    @Override
    public void delete(final Long id) {
        final String DELETE_ILLNESS = "DELETE FROM illness WHERE id = ?";
        jdbcTemplate.update(DELETE_ILLNESS, id);
    }
}
