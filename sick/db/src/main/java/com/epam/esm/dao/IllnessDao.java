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

    public List<Illness> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        criteriaQuery.select(criteriaQuery.from(Illness.class));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void create(Illness illness) {
        entityManager.persist(illness);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.find(Illness.class, id));
    }

    public List<Illness> existsById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        Root<Illness> userRoot = criteriaQuery.from(Illness.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Illness findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        Root<Illness> userRoot = criteriaQuery.from(Illness.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("name"), name));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Illness> findByNameWithDifferentId(Long id, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        Root<Illness> userRoot = criteriaQuery.from(Illness.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("name"), name), criteriaBuilder.and(criteriaBuilder.notEqual(userRoot.get("id"), id)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Illness update(Illness illness) {
        return entityManager.merge(illness);
    }

    public List<Illness> existsByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Illness> criteriaQuery = criteriaBuilder.createQuery(Illness.class);
        Root<Illness> userRoot = criteriaQuery.from(Illness.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("name"), name));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
