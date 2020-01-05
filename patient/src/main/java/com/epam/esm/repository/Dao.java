package com.epam.esm.repository;

import com.epam.esm.repository.entity.Entity;

public interface Dao<T extends Entity> {
    T get(Long entity);

    void create(T entity);

    void update(T entity);

    void delete(Long id);
}
