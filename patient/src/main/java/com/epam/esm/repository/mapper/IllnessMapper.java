package com.epam.esm.repository.mapper;

import com.epam.esm.repository.entity.Illness;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IllnessMapper implements RowMapper<Illness> {
    @Override
    public Illness mapRow(final ResultSet resultSet, final int i) throws SQLException {
        Illness illness = new Illness();
        illness.setId(resultSet.getLong(1));
        illness.setName(resultSet.getString(2));
        illness.setNameInLatin(resultSet.getString(3));
        illness.setChanceToDie(resultSet.getInt(4));
        return illness;
    }
}
