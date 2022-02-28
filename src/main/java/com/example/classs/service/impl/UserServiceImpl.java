package com.example.classs.service.impl;

import com.example.classs.entity.User;
import com.example.classs.repository.UserRepository;
import com.example.classs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean login(User user) {
        User tmpUser = userRepository.findUserByName(user.getName());
        if (Objects.nonNull(tmpUser) && Objects.equals(user.getPassword(), tmpUser.getPassword())) {
            return true;
        }
        return false;
    }

}
