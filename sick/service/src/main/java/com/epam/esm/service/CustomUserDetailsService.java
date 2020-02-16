package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(userDao.findByUsername(username));
    }

    public UserDetails loadUserById(Long id) {
        return new UserPrincipal(userDao.findById(id));
    }
}
