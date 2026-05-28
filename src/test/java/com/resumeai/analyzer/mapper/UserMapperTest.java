/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.mapper;

import com.resumeai.analyzer.dto.response.UserDataResponse;
import com.resumeai.analyzer.enums.RoleEnum;
import com.resumeai.analyzer.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testMapToResponseSuccess() {
        LocalDateTime now = LocalDateTime.now();
        User user =
                User.builder()
                        .id("123")
                        .name("John Doe")
                        .email("john@example.com")
                        .role(RoleEnum.USER)
                        .createdAt(now)
                        .passwordHash("hashed_password")
                        .build();

        UserDataResponse response = UserMapper.mapToResponse(user);

        assertNotNull(response);
        assertEquals("123", response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(RoleEnum.USER, response.getRole());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testMapToResponseWithAdminRole() {
        User user =
                User.builder()
                        .id("456")
                        .name("Admin User")
                        .email("admin@example.com")
                        .role(RoleEnum.ADMIN)
                        .createdAt(LocalDateTime.now())
                        .build();

        UserDataResponse response = UserMapper.mapToResponse(user);

        assertNotNull(response);
        assertEquals(RoleEnum.ADMIN, response.getRole());
        assertEquals("admin@example.com", response.getEmail());
    }

    @Test
    void testMapToResponseWithNulls() {
        User user =
                User.builder()
                        .id("789")
                        .name(null)
                        .email("test@example.com")
                        .role(RoleEnum.USER)
                        .createdAt(null)
                        .build();

        UserDataResponse response = UserMapper.mapToResponse(user);

        assertNotNull(response);
        assertNull(response.getName());
        assertEquals("test@example.com", response.getEmail());
        assertNull(response.getCreatedAt());
    }

    @Test
    void testMapToResponseAllFields() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 5, 28, 10, 30, 0);
        User user =
                User.builder()
                        .id("user123")
                        .name("Jane Smith")
                        .email("jane@example.com")
                        .role(RoleEnum.USER)
                        .createdAt(createdAt)
                        .passwordHash("some_hash")
                        .updatedAt(LocalDateTime.of(2026, 5, 28, 15, 45, 0))
                        .build();

        UserDataResponse response = UserMapper.mapToResponse(user);

        assertEquals("user123", response.getId());
        assertEquals("Jane Smith", response.getName());
        assertEquals("jane@example.com", response.getEmail());
        assertEquals(RoleEnum.USER, response.getRole());
        assertEquals(createdAt, response.getCreatedAt());
    }

    @Test
    void testMapToResponseMultipleUsers() {
        User[] users = {
                User.builder().id("1").name("User1").email("user1@example.com").role(RoleEnum.USER).build(),
                User.builder().id("2").name("User2").email("user2@example.com").role(RoleEnum.ADMIN).build(),
                User.builder().id("3").name("User3").email("user3@example.com").role(RoleEnum.USER).build()
        };

        for (User user : users) {
            UserDataResponse response = UserMapper.mapToResponse(user);
            assertNotNull(response);
            assertEquals(user.getEmail(), response.getEmail());
            assertEquals(user.getRole(), response.getRole());
        }
    }
}
