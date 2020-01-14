package com.epam.esm.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties")
public class ConnectionConfig {
    @Autowired
    private Environment environment;
    private static final String DRIVER = "dataSourceClassName";
    private static final String USERNAME = "db.user";
    private static final String PASSWORD = "db.pass";
    private static final String URL = "db.url";

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(environment.getProperty(DRIVER));
        hikariConfig.setJdbcUrl(environment.getProperty(URL));
        hikariConfig.setUsername(environment.getProperty(USERNAME));
        hikariConfig.setPassword(environment.getProperty(PASSWORD));
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
