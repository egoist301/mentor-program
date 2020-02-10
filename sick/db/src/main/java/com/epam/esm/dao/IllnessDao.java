package com.epam.esm.dao;

import com.epam.esm.entity.Illness;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class IllnessDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Illness findById(Long id) {
        return entityManager.find(Illness.class, id);
    }

    public List<Illness> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        criteriaQuery.select(criteriaQuery.from(Illness.class));
        return entityManager.createQuery(criteriaQuery).setFirstResult((page == 1) ? page - 1 : (page - 1) * size)
                .setMaxResults(size).getResultList();
    }

    public void create(Illness illness) {
        entityManager.persist(illness);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.find(Illness.class, id));
    }

    public boolean existsById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        Root<Illness> userRoot = criteriaQuery.from(Illness.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("id"), id));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }

    public Illness findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        Root<Illness> userRoot = criteriaQuery.from(Illness.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("name"), name));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public boolean isAnyIllnessExistWithName(Long id, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        Root<Illness> userRoot = criteriaQuery.from(Illness.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("name"), name), criteriaBuilder.and(criteriaBuilder.notEqual(userRoot.get("id"), id)));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }

    public Illness update(Illness illness) {
        return entityManager.merge(illness);
    }

    public boolean existsByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        Root<Illness> userRoot = criteriaQuery.from(Illness.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("name"), name));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }
}