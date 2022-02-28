package com.example.classs.service;

import com.example.classs.entity.User;
import com.example.classs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {


    boolean login(User user);
}
