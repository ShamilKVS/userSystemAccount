package com.example.userAccountSystem.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        createDatabase();
        executeScripts("src/main/resources/sql/");
    }

    private void executeScripts(String scriptDirectory) throws Exception {
        List<String> scripts = Files.walk(Paths.get(scriptDirectory))
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().matches("V\\d+__.*\\.sql"))
                .sorted()  // Sort by version number
                .map(path -> path.toString())
                .collect(Collectors.toList());

        for (String scriptPath : scripts) {
            String version = Paths.get(scriptPath).getFileName().toString();
            if (!isScriptExecuted(version)) {
                String sql = new String(Files.readAllBytes(Paths.get(scriptPath)));
                jdbcTemplate.execute(sql);
                markScriptAsExecuted(version);
                System.out.println("Executed script: " + version);
            }
        }
    }

    private boolean isScriptExecuted(String version) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM schema_version WHERE version = ?",
                Integer.class,
                version
        );
        return count != null && count > 0;
    }

    private void markScriptAsExecuted(String version) {
        jdbcTemplate.update("INSERT INTO schema_version (version) VALUES (?)", version);
    }
    private void createDatabase() throws Exception {
        String sql = "CREATE DATABASE IF NOT EXISTS user_account;";
        jdbcTemplate.execute(sql);
        sql = "CREATE TABLE IF NOT EXISTS schema_version (\n" +
                "    version VARCHAR(255) PRIMARY KEY,\n" +
                "    executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                ");";
        jdbcTemplate.execute(sql);
    }
}
