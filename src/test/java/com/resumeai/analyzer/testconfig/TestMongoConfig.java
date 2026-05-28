/*
 * Copyright (c) 2026 Satish Bogadula. All rights reserved.
 */

package com.resumeai.analyzer.testconfig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@TestConfiguration
public class TestMongoConfig {

    @Bean
    public MongoMappingContext mongoMappingContext() {
        return new MongoMappingContext();
    }
}
