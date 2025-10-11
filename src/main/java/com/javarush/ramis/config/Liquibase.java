package com.javarush.ramis.config;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Liquibase {
    private final Properties properties;

    public Liquibase() {
        properties = loadProperties();
    }

    public void start() {
        System.out.println("Running Liquibase...");
        try {
            Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {

                String dbUrl = getEnvOrDefault("DB_URL", properties.getProperty("hibernate.connection.url"));
                String dbUser = getEnvOrDefault("DB_USER", properties.getProperty("hibernate.connection.username"));
                String dbPassword = getEnvOrDefault("DB_PASSWORD", properties.getProperty("hibernate.connection.password"));

                CommandScope update = new CommandScope("update");
                update.addArgumentValue("changelogFile", "db/changelog.xml");
                update.addArgumentValue("url", dbUrl);
                update.addArgumentValue("username", dbUser);
                update.addArgumentValue("password", dbPassword);
                update.execute();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Running Liquibase...DONE");
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = SessionCreator
                .class.getClassLoader().getResourceAsStream("hibernate.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private String getEnvOrDefault(String env, String defaultValue) {
        String value = System.getenv(env);
        if (value != null && !value.trim().isEmpty()) {
            return value;
        } else {
            return defaultValue;
        }
    }
}
