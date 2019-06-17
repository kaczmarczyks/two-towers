package com.kaczmarczyks.twotowers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
public class AutoconfigurationConfig {
}
