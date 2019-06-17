package com.kaczmarczyks.twotowers.second;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.EntityManagerFactoryDependsOnPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class SecondLiquibaseConfig {

    @Autowired
    private Environment env;

    @Bean
    @DependsOn("secondLiquibase")
    String secondCoordinatingBean() {
        return "secondCoordinatingBean";
    }

    @Bean
    public SpringLiquibase secondLiquibase(
            @Qualifier("secondDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(env.getProperty("spring.second-liquibase.change-log"));
        liquibase.setDataSource(dataSource);
        liquibase.setBeanName("secondLiquibase");
        return liquibase;
    }

    @Configuration
    @ConditionalOnClass({LocalContainerEntityManagerFactoryBean.class})
    @ConditionalOnBean({AbstractEntityManagerFactoryBean.class})
    protected static class LiquibaseJpaDependencyConfiguration extends EntityManagerFactoryDependsOnPostProcessor {
        public LiquibaseJpaDependencyConfiguration() {
            super("secondLiquibase");
        }
    }
}
