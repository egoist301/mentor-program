package com.epam.esm.dao;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public User findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("username"), username));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }


    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public void create(User user) {
        entityManager.persist(user);
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    public boolean existsById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("id"), id));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }

    public List<User> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.select(criteriaQuery.from(User.class));
        return entityManager.createQuery(criteriaQuery).setFirstResult((page == 1) ? page - 1 : (page - 1) * size)
                .setMaxResults(size).getResultList();
    }

    public boolean isAnyUserExistWithUsername(Long id, String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("username"), username),
                criteriaBuilder.and(criteriaBuilder.notEqual(userRoot.get("id"), id)));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }

    public boolean existsByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                userRoot.get("username"), username));
        return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
    }

    public Integer getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.select(criteriaQuery.from(User.class));
        return entityManager.createQuery(criteriaQuery).getResultList().size();
        //return (Integer) entityManager.createQuery("SELECT COUNT(*) FROM users").getSingleResult();
        //return (Integer) entityManager.createNativeQuery("SELECT COUNT(*) FROM users").getSingleResult();
    }
}
