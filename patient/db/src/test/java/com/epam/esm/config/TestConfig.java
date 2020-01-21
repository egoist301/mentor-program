package com.epam.esm.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

@Configuration
public class TestConfig {
    private static final String SCHEMA_CREATION_SCRIPT_FILE_PATH = "db/src/test/resources/create_table.sql";
    private static final String TEST_DATA_SCRIPT_SILE_PATH = "db/src/test/resources/fill_table.sql";

    @Profile("dev")
    @Bean
    public DataSource dataSource() throws SQLException, IOException {
        DataSource dataSource = EmbeddedPostgres.start().getTemplateDatabase();
        executeSqlScriptOnDataSource(dataSource, SCHEMA_CREATION_SCRIPT_FILE_PATH);
        executeSqlScriptOnDataSource(dataSource, TEST_DATA_SCRIPT_SILE_PATH);
        return dataSource;
    }

    private void executeSqlScriptOnDataSource(DataSource dataSource, String filePath)
            throws FileNotFoundException, SQLException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine());
        }
        dataSource.getConnection().prepareStatement(builder.toString()).execute();
    }

}

