package com.nnk.springboot.config;

import org.springframework.context.annotation.Configuration;

/**
 * JPA Auditing configuration.
 * Note: Currently disabled as auditing annotations are not used in entities.
 * Uncomment @EnableJpaAuditing if auditing features are needed in the future.
 */
@Configuration
// @EnableJpaAuditing - Disabled: no entities use @CreatedDate/@LastModifiedDate
public class JpaAuditingConfig {
}

