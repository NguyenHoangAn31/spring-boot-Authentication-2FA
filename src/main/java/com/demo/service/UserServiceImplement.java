package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.repository.UserRepository;

@Service
public class UserServiceImplement implements UserService{
    @Autowired
    private UserRepository userRepository;
    
    
}
