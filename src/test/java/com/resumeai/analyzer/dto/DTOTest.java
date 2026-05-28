/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.dto;

import com.resumeai.analyzer.dto.request.LoginRequest;
import com.resumeai.analyzer.dto.request.RegisterRequest;
import com.resumeai.analyzer.dto.response.ErrorResponse;
import com.resumeai.analyzer.dto.response.LoginResponse;
import com.resumeai.analyzer.dto.response.UserDataResponse;
import com.resumeai.analyzer.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DTOTest {

    @Test
    void testLoginRequestSettersAndGetters() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@example.com");
        request.setPasswordHash("password123");

        assertEquals("john@example.com", request.getEmail());
        assertEquals("password123", request.getPasswordHash());
    }

    @Test
    void testLoginRequestConstructor() {
        LoginRequest request = new LoginRequest();
        assertNotNull(request);
    }

    @Test
    void testRegisterRequestSettersAndGetters() {
        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPasswordHash("password123");

        assertEquals("John Doe", request.getName());
        assertEquals("john@example.com", request.getEmail());
        assertEquals("password123", request.getPasswordHash());
    }

    @Test
    void testLoginResponseBuilder() {
        LoginResponse response =
                LoginResponse.builder()
                        .token("jwt_token")
                        .email("john@example.com")
                        .role(RoleEnum.USER)
                        .build();

        assertEquals("jwt_token", response.getToken());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(RoleEnum.USER, response.getRole());
    }

    @Test
    void testLoginResponseSettersAndGetters() {
        LoginResponse response = new LoginResponse("token123", "user@example.com", RoleEnum.ADMIN);

        assertEquals("token123", response.getToken());
        assertEquals("user@example.com", response.getEmail());
        assertEquals(RoleEnum.ADMIN, response.getRole());
    }

    @Test
    void testUserDataResponseBuilder() {
        LocalDateTime now = LocalDateTime.now();
        UserDataResponse response =
                UserDataResponse.builder()
                        .id("123")
                        .name("John Doe")
                        .email("john@example.com")
                        .role(RoleEnum.USER)
                        .createdAt(now)
                        .build();

        assertEquals("123", response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(RoleEnum.USER, response.getRole());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testUserDataResponseSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        UserDataResponse response =
                new UserDataResponse("456", "Jane Smith", "jane@example.com", RoleEnum.ADMIN, now);

        assertEquals("456", response.getId());
        assertEquals("Jane Smith", response.getName());
        assertEquals("jane@example.com", response.getEmail());
        assertEquals(RoleEnum.ADMIN, response.getRole());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testErrorResponseBuilder() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse response =
                ErrorResponse.builder().status(404).message("Not found").timeStamp(now).build();

        assertEquals(404, response.getStatus());
        assertEquals("Not found", response.getMessage());
        assertEquals(now, response.getTimeStamp());
    }

    @Test
    void testErrorResponseConstructor() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse response = new ErrorResponse(500, "Internal server error", now);

        assertEquals(500, response.getStatus());
        assertEquals("Internal server error", response.getMessage());
        assertEquals(now, response.getTimeStamp());
    }

    @Test
    void testErrorResponseSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse response = new ErrorResponse(401, "Unauthorized", now);
        response.setStatus(403);
        response.setMessage("Forbidden");

        assertEquals(403, response.getStatus());
        assertEquals("Forbidden", response.getMessage());
        assertEquals(now, response.getTimeStamp());
    }

    @Test
    void testMultipleErrorResponseStatuses() {
        int[] statusCodes = {400, 401, 403, 404, 409, 500, 503};

        for (int status : statusCodes) {
            ErrorResponse response =
                    ErrorResponse.builder()
                            .status(status)
                            .message("Error " + status)
                            .timeStamp(LocalDateTime.now())
                            .build();

            assertEquals(status, response.getStatus());
            assertEquals("Error " + status, response.getMessage());
        }
    }
}
