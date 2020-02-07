package com.epam.esm.dao;

import com.epam.esm.entity.Doctor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class DoctorDao {
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String DATE_OF_BIRTH = "date_of_birth";
    private static final String ALL_FIELDS =
            "doctor.id, first_name, last_name, middle_name, phone_number, date_of_birth, price_per_consultation, identification_number, doctor.create_date, doctor.update_date";
    @PersistenceContext
    private EntityManager entityManager;

    public Doctor findById(Long id) {
        return entityManager.find(Doctor.class, id);
    }

    public void create(Doctor doctor) {
        entityManager.merge(doctor);
    }

    public Doctor update(Doctor doctor) {
        return entityManager.merge(doctor);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.find(Doctor.class, id));
    }

    public boolean existsById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
        Root<Doctor> userRoot = criteriaQuery.from(Doctor.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("id"), id));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }

    public List<Doctor> findAll(List<String> filters, String sortBy, String order) {
        return entityManager.createNativeQuery(getQuery(sortBy, order), Doctor.class)
                .setParameter("firstName", filters.get(0)).setParameter("lastName", filters.get(1))
                .setParameter("middleName", filters.get(2)).setParameter("name", filters.get(3)).getResultList();
    }

    private String getQuery(String sortBy, String order) {
        String search =
                new StringBuilder().append("SELECT ")
                        .append("id, first_name, last_name, middle_name, phone_number, date_of_birth, price_per_consultation, identification_number, create_date, update_date")
                        .append(" FROM searchPatient(:firstName,:lastName,:middleName,:name)")
                        .toString();
        if (sortBy != null && (sortBy.equals(FIRST_NAME) || sortBy.equals(LAST_NAME) || sortBy.equals(MIDDLE_NAME) ||
                sortBy.equals(DATE_OF_BIRTH))) {
            search = search.concat(" ORDER BY ".concat(sortBy));

            if (order != null && (order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc"))) {
                search = search.concat(" ".concat(order));
            }
        }
        return search;
    }

    public Doctor findByIdentificationNumber(String identificationNumber) {
        return (Doctor) entityManager.createNativeQuery("SELECT " + ALL_FIELDS
                        + " FROM doctor JOIN doctor_illness di on doctor.id = di.doctor_id JOIN illness i on di.illness_id = i.id WHERE identification_number = :identification_number",
                Doctor.class).setParameter("identification_number", identificationNumber).getSingleResult();
    }

    public boolean existByIdentificationNumber(String number) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
        Root<Doctor> userRoot = criteriaQuery.from(Doctor.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("identification_number"), number));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }
}
