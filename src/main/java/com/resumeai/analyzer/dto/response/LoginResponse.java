/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.dto.response;

import com.resumeai.analyzer.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private RoleEnum role;
}
