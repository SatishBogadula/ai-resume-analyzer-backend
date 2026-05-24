/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.service;

import com.resumeai.analyzer.dto.request.LoginRequest;
import com.resumeai.analyzer.dto.request.RegisterRequest;
import com.resumeai.analyzer.dto.response.LoginResponse;
import com.resumeai.analyzer.dto.response.UserDataResponse;
import com.resumeai.analyzer.enums.RoleEnum;
import com.resumeai.analyzer.exception.InvalidCredentialsException;
import com.resumeai.analyzer.exception.UserAlreadyExistsException;
import com.resumeai.analyzer.exception.UserNotFoundException;
import com.resumeai.analyzer.mapper.UserMapper;
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

    public UserDataResponse registerUser(RegisterRequest request) {

        userRepository
                .findByEmail(request.getEmail())
                .ifPresent(
                        existing -> {
                            throw new UserAlreadyExistsException(
                                    "User already exists with email " + request.getEmail());
                        });

        User user =
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .passwordHash(passwordEncoder.encode(request.getPasswordHash()))
                        .role(RoleEnum.USER)
                        .build();

        User savedUser = userRepository.save(user);

        return UserMapper.mapToResponse(savedUser);
    }

    public UserDataResponse getUserByEmail(String email) {

        User user = findUserByEmail(email);
        return UserMapper.mapToResponse(user);
    }

    public LoginResponse loginUser(LoginRequest request) {
        User user = findUserByEmail(request.getEmail());

        boolean isPasswordValid =
                passwordEncoder.matches(request.getPasswordHash(), user.getPasswordHash());

        if (!isPasswordValid) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        return LoginResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token("temp token")
                .build();
    }

    private User findUserByEmail(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
