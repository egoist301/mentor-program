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

    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    public void create(Order order) {
        entityManager.merge(order);
    }

    public boolean existsById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> userRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("id"), id));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }

    public List<Order> findAllForCurrentUser(Long userId, int page, int size) {
        return entityManager
                .createNativeQuery("SELECT id, create_date, total_price, user_id FROM orders WHERE user_id = :user_ID",
                        Order.class)
                .setParameter("user_ID", userId).setFirstResult((page == 1) ? page - 1 : (page - 1) * size)
                .setMaxResults(size).getResultList();
    }

    public List<Order> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        criteriaQuery.select(criteriaQuery.from(Order.class));
        return entityManager.createQuery(criteriaQuery).setFirstResult((page == 1) ? page - 1 : (page - 1) * size)
                .setMaxResults(size).getResultList();
    }

    public boolean existsByIdForCurrentUser(Long orderId, Long userId) {
        return !entityManager
                .createNativeQuery(
                        "SELECT id, create_date, total_price, user_id FROM orders WHERE user_id = :user_ID AND id = :id",
                        Order.class)
                .setParameter("user_ID", userId).setParameter("id", orderId).getResultList().isEmpty();
    }
}
