package com.kaczmarczyks.twotowers.first;

import com.google.common.collect.ImmutableMap;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@EnableJpaRepositories(
        basePackages = "com.kaczmarczyks.twotowers.first",
        entityManagerFactoryRef = "firstEntityManagerFactory",
        transactionManagerRef = "firstTransactionManager")
@Configuration
public class Config {


    @Autowired
    private Environment env;

    private Map<String, Object> getJpaProperties(Environment env) {
        return ImmutableMap.of(
                "hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName(),
                "hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName(),
                "hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"),
                "hibernate.dialect", env.getProperty("spring.jpa.database-platform")
        );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(
            @Qualifier("firstDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        return builder.dataSource(dataSource)
                .packages("com.kaczmarczyks.twotowers.first")
                .properties(getJpaProperties(env))
                .persistenceUnit("first")
                .build();
    }

    @Bean
    public DataSource firstDataSource() {
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.url"))
                .driverClassName(env.getProperty("spring.datasource.driverClassName"))
                .build();
    }

    @Bean
    public PlatformTransactionManager firstTransactionManager(
            @Qualifier("firstDataSource") DataSource dataSource,
            @Qualifier("firstEntityManagerFactory") EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("firstDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(env.getProperty("spring.liquibase.change-log"));
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}