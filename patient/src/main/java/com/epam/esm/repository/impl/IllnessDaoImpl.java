package com.epam.esm.repository.impl;

import com.epam.esm.repository.IllnessDao;
import com.epam.esm.repository.entity.Illness;
import com.epam.esm.repository.mapper.IllnessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class IllnessDaoImpl implements IllnessDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String GET_ILLNESS_BY_ID =
            "SELECT id, name, name_in_latin, chance_to_die FROM illness WHERE id = ?";
    private static final String CREATE_ILLNESS =
            "INSERT INTO illness(name, name_in_latin, chance_to_die) VALUES (?,?,?)";
    private static final String DELETE_ILLNESS = "DELETE FROM illness WHERE id = ?";

    @Override
    public Illness get(final Long id) {
        return jdbcTemplate.queryForObject(GET_ILLNESS_BY_ID, new IllnessMapper(), id);
    }

    @Override
    public void create(final Illness entity) {
        jdbcTemplate.update(CREATE_ILLNESS, entity.getName(), entity.getNameInLatin(), entity.getChanceToDie());
    }

    @Override
    public void update(final Illness entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Long id) {
        jdbcTemplate.update(DELETE_ILLNESS, id);
    }
}
