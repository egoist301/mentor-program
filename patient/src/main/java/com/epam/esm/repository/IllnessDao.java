package com.epam.esm.repository;

import com.epam.esm.repository.entity.Illness;

public interface IllnessDao {
    Illness get(Long id);

    void create(Illness entity);

    void delete(Long id);
}
