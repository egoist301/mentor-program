package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public void create(User user) {
        userDao.create(user);
    }

    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    public boolean isUserExist(Long id) {
        return userDao.existsById(id);
    }

    public boolean isUserExist(String username) {
        return userDao.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public boolean isAnyUserExistWithUsername(Long id, String username) {
        return userDao.isAnyUserExistWithUsername(id, username);
    }
}
