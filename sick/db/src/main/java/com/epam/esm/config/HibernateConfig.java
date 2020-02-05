package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class HibernateConfig {
    private final Environment environment;
    private static final String DIALECT = "spring.jpa.properties.hibernate.dialect";
    private static final String SHOW_SQL = "spring.jpa.properties.hibernate.show_sql";
    private static final String FORMAT_SQL = "spring.jpa.properties.hibernate.format_sql";
    private static final String USERNAME = "spring.datasource.username";
    private static final String PASSWORD = "spring.datasource.password";
    private static final String URL = "spring.datasource.url";

    @Autowired
    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty(URL));
        hikariConfig.setUsername(environment.getProperty(USERNAME));
        hikariConfig.setPassword(environment.getProperty(PASSWORD));
        return new HikariDataSource(hikariConfig);
    }


    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.epam.esm.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> jpaProperty = factoryBean.getJpaPropertyMap();
        jpaProperty.put("hibernate.dialect", environment.getProperty(DIALECT));
        jpaProperty.put("hibernate.show_sql", environment.getProperty(SHOW_SQL));
        jpaProperty.put("hibernate.format_sql", environment.getProperty(FORMAT_SQL));
        return factoryBean;
    }
}
