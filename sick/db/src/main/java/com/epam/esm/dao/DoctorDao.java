package com.epam.esm.dao;

import com.epam.esm.entity.Doctor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    public List<Doctor> findAll(List<String> filtersByMainEntity, List<String> illnesses, String sortBy, String order,
                                int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
        Root<Doctor> root = criteriaQuery.from(Doctor.class);
        List<Predicate> predicates = createPredicates(illnesses, criteriaBuilder, filtersByMainEntity, root);
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        addSort(sortBy, order, criteriaBuilder, root, criteriaQuery);
        return entityManager.createQuery(criteriaQuery).setFirstResult((page == 1) ? page - 1 : (page - 1) * size)
                .setMaxResults(size).getResultList();
    }

    private List<Predicate> createPredicates(List<String> illnesses, CriteriaBuilder criteriaBuilder,
                                             List<String> filtersByMainEntity, Root<Doctor> root) {
        List<Predicate> predicates = new ArrayList<>();
        illnesses.forEach(name -> predicates.add(criteriaBuilder.equal(root.join("illnesses").get("name"), name)));
        predicates.add(criteriaBuilder
                .and(criteriaBuilder.like(root.get("firstName"), '%' + filtersByMainEntity.get(0) + '%'),
                        criteriaBuilder.and(criteriaBuilder
                                .like(root.get("middleName"), '%' + filtersByMainEntity.get(1) + '%')),
                        criteriaBuilder.and(criteriaBuilder
                                .like(root.get("lastName"), '%' + filtersByMainEntity.get(2) + '%'))));
        return predicates;
    }

    private void addSort(String sortBy, String order, CriteriaBuilder criteriaBuilder, Root<Doctor> root,
                         CriteriaQuery<Doctor> criteriaQuery) {
        if ("asc".equalsIgnoreCase(order)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortBy)));
        }
        if ("desc".equalsIgnoreCase(order)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortBy)));
        }
    }

    public Doctor findByIdentificationNumber(String identificationNumber) {
        return (Doctor) entityManager.createNativeQuery("SELECT " + ALL_FIELDS
                        + " FROM doctor WHERE identification_number = :identification_number",
                Doctor.class).setParameter("identification_number", identificationNumber).getSingleResult();
    }

    public boolean existByIdentificationNumber(String number) {
        return !entityManager.createNativeQuery("SELECT " + ALL_FIELDS
                        + " FROM doctor JOIN doctor_illness di on doctor.id = di.doctor_id JOIN illness i on di.illness_id = i.id WHERE identification_number = :identification_number",
                Doctor.class).setParameter("identification_number", number).getResultList().isEmpty();
    }

    public List<Doctor> findAllForCurrentUser(Long userId, int page, int size) {
        return entityManager.createNativeQuery("SELECT DISTINCT " + ALL_FIELDS
                + " FROM doctor JOIN order_doctor od on doctor.id = od.doctor_id JOIN orders o on od.order_id = o.id WHERE user_id = :userId", Doctor.class)
                .setParameter("userId", userId).setFirstResult((page == 1) ? page - 1 : (page - 1) * size)
                .setMaxResults(size).getResultList();
    }
}
