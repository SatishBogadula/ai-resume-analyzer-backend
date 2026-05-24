/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.mapper;

import com.resumeai.analyzer.dto.response.UserDataResponse;
import com.resumeai.analyzer.model.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDataResponse mapToResponse(User user) {

        return UserDataResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
