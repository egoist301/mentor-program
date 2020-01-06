package com.epam.esm.repository;

import com.epam.esm.repository.entity.Patient;

public interface PatientDao {
    Patient get(Long id);

    void create(Patient entity);

    void update(Patient entity);

    void delete(Long id);
}
