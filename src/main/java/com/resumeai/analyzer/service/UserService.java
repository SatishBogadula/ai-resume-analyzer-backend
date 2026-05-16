/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.service;


import com.resumeai.analyzer.model.User;
import com.resumeai.analyzer.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        userRepository.findByEmail(user.getEmail())
                .ifPresent(exsitingUser -> {
                    throw new RuntimeException("User already present with email" + user.getEmail());
                });

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
