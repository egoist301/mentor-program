package com.epam.esm.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class TestConnectionConfig {
    @Profile("dev")
    @Bean
    public DataSource dataSource() throws IOException {
        return EmbeddedPostgres.start().getTemplateDatabase();
    }

    /*@Bean
    public Flyway flyway(DataSource dataSource) {
        FluentConfiguration fluentConfiguration = Flyway.configure().dataSource(dataSource);
        return new Flyway(fluentConfiguration);
    }*/
}
