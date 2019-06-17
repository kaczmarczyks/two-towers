package com.kaczmarczyks.twotowers.second;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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
        basePackages = "com.kaczmarczyks.twotowers.second",
        entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager")
@Configuration
public class SecondConfig {


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
    @DependsOn("secondCoordinatingBean")
    public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
            @Qualifier("secondDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.kaczmarczyks.twotowers.second");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(getJpaProperties(env));
        em.setPersistenceUnitName("second");
        return em;
    }

    @Bean
    public DataSource secondDataSource() {
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.second-datasource.url"))
                .driverClassName(env.getProperty("spring.second-datasource.driverClassName"))
                .build();
    }

    @Bean
    public PlatformTransactionManager secondTransactionManager(
            @Qualifier("secondDataSource") DataSource dataSource,
            @Qualifier("secondEntityManagerFactory") EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }
}