package com.epam.esm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "first_name", length = 20)
    private String firstName;
    @Column(name = "last_name", length = 20)
    private String lastName;
    @Column(name = "middle_name", length = 20)
    private String middleName;
    @Column(name = "phone_number")
    private Integer phoneNumber;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "identification_number", unique = true, updatable = false, length = 14)
    private String identificationNumber;
    @Column(name = "price_per_consultation")
    private BigDecimal pricePerConsultation;
    @Column(name = "create_date", updatable = false)
    private LocalDate createDate;
    @Column(name = "update_date")
    private LocalDate updateDate;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "doctor_illness",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "illness_id")})
    private Set<Illness> illnesses;

    public Doctor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public BigDecimal getPricePerConsultation() {
        return pricePerConsultation;
    }

    public void setPricePerConsultation(BigDecimal pricePerConsultation) {
        this.pricePerConsultation = pricePerConsultation;
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

    public Set<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<Illness> illnesses) {
        this.illnesses = illnesses;
    }

    @PrePersist
    private void onCreate() {
        createDate = LocalDate.now();
        updateDate = LocalDate.now();
    }

    @PreUpdate
    private void onUpdate() {
        updateDate = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        Doctor doctor = (Doctor) o;
        return Objects.equals(getId(), doctor.getId()) &&
                Objects.equals(getFirstName(), doctor.getFirstName()) &&
                Objects.equals(getLastName(), doctor.getLastName()) &&
                Objects.equals(getMiddleName(), doctor.getMiddleName()) &&
                Objects.equals(getPhoneNumber(), doctor.getPhoneNumber()) &&
                Objects.equals(getDateOfBirth(), doctor.getDateOfBirth()) &&
                Objects.equals(getIdentificationNumber(), doctor.getIdentificationNumber()) &&
                Objects.equals(getPricePerConsultation(), doctor.getPricePerConsultation()) &&
                Objects.equals(getCreateDate(), doctor.getCreateDate()) &&
                Objects.equals(getUpdateDate(), doctor.getUpdateDate()) &&
                Objects.equals(getIllnesses(), doctor.getIllnesses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getMiddleName(), getPhoneNumber(), getDateOfBirth(),
                getIdentificationNumber(), getPricePerConsultation(), getCreateDate(), getUpdateDate(), getIllnesses());
    }
}
