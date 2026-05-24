/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.controller;

import com.resumeai.analyzer.dto.request.LoginRequest;
import com.resumeai.analyzer.dto.request.RegisterRequest;
import com.resumeai.analyzer.dto.response.LoginResponse;
import com.resumeai.analyzer.dto.response.UserDataResponse;
import com.resumeai.analyzer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ResumeAnalyzerController {

    private final UserService userService;

    public ResumeAnalyzerController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDataResponse> registerUser(
            @RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<UserDataResponse> getUserByEmail(@PathVariable String email) {

        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
