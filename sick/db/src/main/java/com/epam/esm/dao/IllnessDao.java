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
    private static final String FIND_MOST_USED_ILLNESS_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS
            =
            "with user_with_highest_cost_of_all_orders as (select o.user_id from orders o JOIN order_doctor od ON od.order_id = o.id JOIN doctor_illness di ON di.doctor_id = od.doctor_id "
                    + "JOIN illness i ON i.id = di.illness_id group by o.user_id order by sum(o.total_price) desc limit 1), "
                    + "most_used_illness as (SELECT i.id, i.name, i.description, i.chance_to_die, i.update_date, i.create_date, count(*) as count_illness FROM orders o "
                    + "JOIN order_doctor od ON od.order_id = o.id JOIN doctor_illness di ON di.doctor_id = od.doctor_id "
                    + "JOIN illness i ON i.id = di.illness_id "
                    + "where o.user_id = (select user_id from user_with_highest_cost_of_all_orders) group by i.id"
                    + " order by count(*) desc limit 1) select illness.id, illness.name, illness.description, illness.chance_to_die, illness.create_date, illness.update_date from most_used_illness as illness";
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


    public Illness findWidelyUsed() {
        return (Illness) entityManager
                .createNativeQuery(FIND_MOST_USED_ILLNESS_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS, Illness.class)
                .getSingleResult();
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
