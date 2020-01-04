package com.epam.esm.repository.entity;

import java.io.Serializable;
import java.util.Objects;

public abstract class Entity implements Serializable {
    private Long id;

    public Entity() {

    }

    public Entity(final Long idNew) {
        id = idNew;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long idNew) {
        id = idNew;
    }

    @Override
    public boolean equals(final Object oNew) {
        if (this == oNew) {
            return true;
        }
        if (!(oNew instanceof Entity)) {
            return false;
        }
        Entity entity = (Entity) oNew;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "id=" + id + '}';
    }
}
