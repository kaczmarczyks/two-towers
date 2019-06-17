package com.kaczmarczyks.twotowers.second;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondLiquibaseConfig {

    @Bean
    String secondCoordinatingBean() {
        return "secondCoordinatingBean";
    }

}
