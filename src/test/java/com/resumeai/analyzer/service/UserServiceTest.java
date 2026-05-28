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
import com.resumeai.analyzer.model.User;
import com.resumeai.analyzer.repository.UserRepository;
import com.resumeai.analyzer.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPasswordHash("password123");

        User savedUser =
                User.builder()
                        .id("123")
                        .name("John Doe")
                        .email("john@example.com")
                        .passwordHash("encoded_password")
                        .role(RoleEnum.USER)
                        .createdAt(LocalDateTime.now())
                        .build();

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDataResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals("john@example.com", response.getEmail());
        assertEquals("John Doe", response.getName());
        assertEquals(RoleEnum.USER, response.getRole());
        verify(userRepository, times(1)).findByEmail("john@example.com");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("existing@example.com");
        request.setPasswordHash("password123");

        User existingUser =
                User.builder().id("123").name("Existing User").email("existing@example.com").build();

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(request));
        verify(userRepository, times(1)).findByEmail("existing@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetUserByEmailSuccess() {
        User user =
                User.builder()
                        .id("123")
                        .name("John Doe")
                        .email("john@example.com")
                        .role(RoleEnum.USER)
                        .createdAt(LocalDateTime.now())
                        .build();

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        UserDataResponse response = userService.getUserByEmail("john@example.com");

        assertNotNull(response);
        assertEquals("john@example.com", response.getEmail());
        assertEquals("John Doe", response.getName());
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    @Test
    void testGetUserByEmailNotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class, () -> userService.getUserByEmail("notfound@example.com"));
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    void testLoginUserSuccess() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@example.com");
        request.setPasswordHash("password123");

        User user =
                User.builder()
                        .id("123")
                        .name("John Doe")
                        .email("john@example.com")
                        .passwordHash("encoded_password")
                        .role(RoleEnum.USER)
                        .build();

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(true);
        when(jwtService.generateToken("john@example.com")).thenReturn("jwt_token_123");

        LoginResponse response = userService.loginUser(request);

        assertNotNull(response);
        assertEquals("john@example.com", response.getEmail());
        assertEquals("jwt_token_123", response.getToken());
        assertEquals(RoleEnum.USER, response.getRole());
        verify(userRepository, times(1)).findByEmail("john@example.com");
        verify(passwordEncoder, times(1)).matches("password123", "encoded_password");
        verify(jwtService, times(1)).generateToken("john@example.com");
    }

    @Test
    void testLoginUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("notfound@example.com");
        request.setPasswordHash("password123");

        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.loginUser(request));
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    void testLoginUserInvalidPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@example.com");
        request.setPasswordHash("wrongpassword");

        User user =
                User.builder()
                        .id("123")
                        .email("john@example.com")
                        .passwordHash("encoded_password")
                        .role(RoleEnum.USER)
                        .build();

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "encoded_password")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(request));
        verify(userRepository, times(1)).findByEmail("john@example.com");
        verify(passwordEncoder, times(1)).matches("wrongpassword", "encoded_password");
        verify(jwtService, never()).generateToken(anyString());
    }
}
