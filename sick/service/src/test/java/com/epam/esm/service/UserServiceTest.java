package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityIsNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    private User user;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userDao);
        user = new User();
        user.setId(1L);
        user.setRole(Role.ROLE_USER);
        user.setCreateDate(LocalDate.now());
        user.setUpdateDate(LocalDate.now());
        user.setUsername("username");
        user.setPassword("password");
    }

    @Test
    public void findById_setCorrectId_shouldBeReturnUser() {
        when(userDao.findById(1L)).thenReturn(user);
        when(userDao.existsById(1L)).thenReturn(true);
        assertEquals(user, userService.findById(1L));
    }

    @Test(expected = EntityIsNotExistException.class)
    public void findById_setIncorrectId_shouldBeReturnException() {
        when(userDao.findById(1L)).thenReturn(null);
        when(userDao.existsById(1L)).thenReturn(false);
        assertEquals(user, userService.findById(1L));
    }

    @Test
    public void findAll_setCorrectSizeAndPage_shouldBeReturnListUsers() {
        List<User> users = Collections.singletonList(user);
        when(userDao.findAll(0, 1)).thenReturn(users);
        assertEquals(users, userService.findAll(0, 1));
    }

    @Test
    public void findByUsername_setCorrectUsername_shouldBeReturnUser() {
        String username = "username";
        when(userDao.findByUsername(username)).thenReturn(user);
        assertEquals(user, userService.findByUsername(username));
    }

    @Test(expected = NoResultException.class)
    public void findByUsername_setIncorrectUsername() {
        String username = "dwadwadwa";
        when(userDao.findByUsername(username)).thenThrow(NoResultException.class);
        assertEquals(user, userService.findByUsername(username));
    }

    @Test
    public void existByUsername_setCorrectUsername_shouldBeReturnTrue() {
        String username = "username";
        when(userDao.existsByUsername(username)).thenReturn(true);
        assertTrue(userService.isExistByUsername(username));
    }

    @Test
    public void existByUsername_setIncorrectUsername_shouldBeReturnFalse() {
        String username = "username";
        when(userDao.existsByUsername(username)).thenReturn(false);
        assertFalse(userService.isExistByUsername(username));
    }
}
