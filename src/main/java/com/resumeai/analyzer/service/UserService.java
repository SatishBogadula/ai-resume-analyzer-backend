/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.service;

import com.resumeai.analyzer.dto.UserResponse;
import com.resumeai.analyzer.model.User;
import com.resumeai.analyzer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(User user) {

        userRepository
                .findByEmail(user.getEmail())
                .ifPresent(
                        exsitingUser -> {
                            throw new RuntimeException("User already present with email " + user.getEmail());
                        });

        user.setPassWordHash(passwordEncoder.encode(user.getPassWordHash()));
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public UserResponse getUserByEmail(String email) {

        User user =
                userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
}
