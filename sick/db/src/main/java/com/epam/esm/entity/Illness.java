package com.epam.esm.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "illness")
public class Illness implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "chance_to_die")
    private Integer chanceToDie;
    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDate createDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDate updateDate;

    public Illness() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getChanceToDie() {
        return chanceToDie;
    }

    @PrePersist
    protected void onCreate() {
        createDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDate.now();
    }

    public void setChanceToDie(Integer chanceToDie) {
        this.chanceToDie = chanceToDie;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Illness)) {
            return false;
        }
        Illness illness = (Illness) o;
        return Objects.equals(getId(), illness.getId()) &&
                Objects.equals(getName(), illness.getName()) &&
                Objects.equals(getDescription(), illness.getDescription()) &&
                Objects.equals(getChanceToDie(), illness.getChanceToDie()) &&
                Objects.equals(getCreateDate(), illness.getCreateDate()) &&
                Objects.equals(getUpdateDate(), illness.getUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getChanceToDie(), getCreateDate(), getUpdateDate());
    }
}
