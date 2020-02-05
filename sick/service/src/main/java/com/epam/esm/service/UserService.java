package com.epam.esm.service;

import com.epam.esm.converter.UserDtoConverter;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityIsAlreadyExistException;
import com.epam.esm.exception.EntityIsNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void create(UserRequestDto userRequestDto) {
        User user = UserDtoConverter.convertToEntity(userRequestDto);
        isUserExist(user.getUsername());
        userDao.create(user);
    }

    public UserResponseDto get(Long id) {
        isUserNotExist(id);
        return UserDtoConverter.convertToDto(userDao.findById(id));
    }

    public List<UserResponseDto> getAll() {
        return userDao.findAll().stream().map(UserDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        isUserNotExist(id);
        userDao.delete(id);
    }

    @Transactional
    public void update(Long id, UserRequestDto userRequestDto) {
        User user = UserDtoConverter.convertToEntity(userRequestDto);
        user.setId(id);

        isUserNotExist(id);
        isAnyUserExistWithUsername(id, user.getUsername());

        userDao.update(user);
    }

    private void isUserNotExist(Long id) {
        if (userDao.existsById(id).isEmpty()) {
            throw new EntityIsNotExistException("user is not exist");
        }
    }

    private void isUserExist(String username) {
        if (!userDao.existsByUsername(username).isEmpty()) {
            throw new EntityIsAlreadyExistException("user already exist");
        }
    }

    private void isAnyUserExistWithUsername(Long id, String username) {
        if (!userDao.findByUsernameWithDifferentId(id, username).isEmpty()) {
            throw new EntityIsAlreadyExistException("username with this name:" + username + " already exist");
        }
    }
}
