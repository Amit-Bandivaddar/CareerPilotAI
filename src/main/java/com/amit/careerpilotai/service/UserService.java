package com.amit.careerpilotai.service;

import com.amit.careerpilotai.entity.User;
import com.amit.careerpilotai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        return userRepository.save(user);

    }
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid Password!");
        }

        return user;
    }

}