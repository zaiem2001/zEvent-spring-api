package com.zevent.zevent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zevent.zevent.model.User;
import com.zevent.zevent.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.insert(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
