package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.EntityIsNotExistException;
import com.epam.esm.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userDao.existsByUsername(username)) {
            return new UserPrincipal(userDao.findByUsername(username));
        } else {
            throw new EntityIsNotExistException("user is not register");
        }
    }

    public UserDetails loadUserById(Long id) {
        return new UserPrincipal(userDao.findById(id));
    }
}
