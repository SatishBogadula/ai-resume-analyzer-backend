/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.model;

import com.resumeai.analyzer.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void testUserBuilder() {
        LocalDateTime now = LocalDateTime.now();
        User user =
                User.builder()
                        .id("123")
                        .name("John Doe")
                        .email("john@example.com")
                        .passwordHash("hashed")
                        .role(RoleEnum.USER)
                        .createdAt(now)
                        .updatedAt(now)
                        .build();

        assertEquals("123", user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("hashed", user.getPasswordHash());
        assertEquals(RoleEnum.USER, user.getRole());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testUserDefaultRole() {
        User user = new User();
        assertEquals(RoleEnum.USER, user.getRole());
    }

    @Test
    void testUserWithAdminRole() {
        User user =
                User.builder()
                        .id("456")
                        .name("Admin")
                        .email("admin@example.com")
                        .role(RoleEnum.ADMIN)
                        .build();

        assertEquals(RoleEnum.ADMIN, user.getRole());
    }

    @Test
    void testUserSettersAndGetters() {
        User user = new User();
        user.setId("789");
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        user.setPasswordHash("hashed123");
        user.setRole(RoleEnum.ADMIN);

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertEquals("789", user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("hashed123", user.getPasswordHash());
        assertEquals(RoleEnum.ADMIN, user.getRole());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testUserEquality() {
        User user1 = User.builder().id("123").name("John").email("john@example.com").build();

        User user2 = User.builder().id("123").name("John").email("john@example.com").build();

        assertEquals(user1, user2);
    }

    @Test
    void testUserWithAllFieldsSet() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 5, 1, 10, 0, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2026, 5, 28, 15, 30, 0);

        User user =
                new User(
                        "user1",
                        "Test User",
                        "test@example.com",
                        "encrypted_password",
                        RoleEnum.USER,
                        createdAt,
                        updatedAt);

        assertNotNull(user);
        assertEquals("user1", user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
    }
}
