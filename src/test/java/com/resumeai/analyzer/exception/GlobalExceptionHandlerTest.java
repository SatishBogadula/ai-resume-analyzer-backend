/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.exception;

import com.resumeai.analyzer.dto.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleUserAlreadyExistsException() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");

        ResponseEntity<ErrorResponse> response =
                exceptionHandler.handleUserAlreadyExistsException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(409, response.getBody().getStatus());
        assertEquals("User already exists", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimeStamp());
    }

    @Test
    void testHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<ErrorResponse> response =
                exceptionHandler.handleUserNotFoundException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    void testHandleInvalidCredentialsException() {
        InvalidCredentialsException exception = new InvalidCredentialsException("Invalid credentials");

        ResponseEntity<ErrorResponse> response =
                exceptionHandler.invalidCredentialsException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(401, response.getBody().getStatus());
        assertEquals("Invalid credentials", response.getBody().getMessage());
    }

    @Test
    void testExceptionHandlingWithDifferentMessages() {
        String[] messages = {
                "Error message 1", "Another error occurred", "Critical failure", "Resource not available"
        };

        for (String message : messages) {
            UserNotFoundException exception = new UserNotFoundException(message);
            ResponseEntity<ErrorResponse> response =
                    exceptionHandler.handleUserNotFoundException(exception);

            assertEquals(message, response.getBody().getMessage());
            assertEquals(404, response.getBody().getStatus());
        }
    }

    @Test
    void testAllExceptionHandlersReturnCorrectStatusCodes() {
        UserAlreadyExistsException alreadyExists = new UserAlreadyExistsException("exists");
        UserNotFoundException notFound = new UserNotFoundException("not found");
        InvalidCredentialsException invalidCreds = new InvalidCredentialsException("invalid");

        ResponseEntity<ErrorResponse> response1 =
                exceptionHandler.handleUserAlreadyExistsException(alreadyExists);
        ResponseEntity<ErrorResponse> response2 =
                exceptionHandler.handleUserNotFoundException(notFound);
        ResponseEntity<ErrorResponse> response3 =
                exceptionHandler.invalidCredentialsException(invalidCreds);

        assertEquals(409, response1.getBody().getStatus());
        assertEquals(404, response2.getBody().getStatus());
        assertEquals(401, response3.getBody().getStatus());
    }
}
