/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.controller;

import com.resumeai.analyzer.dto.UserResponse;
import com.resumeai.analyzer.model.User;
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
    public ResponseEntity<UserResponse> registerUser(@RequestBody User user) {

        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {

        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
}
