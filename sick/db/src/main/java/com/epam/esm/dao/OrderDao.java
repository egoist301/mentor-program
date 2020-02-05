package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Order findById(Long id, Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> userRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("id"), id), criteriaBuilder.and(criteriaBuilder.equal(userRoot.get("user_id"), userId)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public void create(Order order) {
        entityManager.merge(order);
    }

    public void update(Order order) {
        entityManager.merge(order);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.find(Order.class, id));
    }

    public List<Order> existsById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> userRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Order> findAll(Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> userRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("user_id"), userId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
