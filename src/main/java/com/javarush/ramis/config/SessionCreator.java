package com.javarush.ramis.config;

import com.javarush.ramis.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class SessionCreator implements AutoCloseable {
    private volatile static SessionCreator instance;
    private final SessionFactory sessionFactory;

    public static SessionCreator getInstance() {
        if (instance == null) {
            synchronized (SessionCreator.class) {
                if (instance == null) {
                    instance = new SessionCreator();
                }
            }
        }
        return instance;
    }

    public static SessionCreator sessionCreatorForTests(Properties properties) {
       return new SessionCreator(properties);
    }

    //for real db
    private SessionCreator() {
        try {
            Configuration configuration = new Configuration();
            Properties properties = loadProperties();

            String dbUrl = getEnvOrDefault("DB_URL", properties.getProperty("hibernate.connection.url"));
            String dbUser = getEnvOrDefault("DB_USER", properties.getProperty("hibernate.connection.username"));
            String dbPassword = getEnvOrDefault("DB_PASSWORD", properties.getProperty("hibernate.connection.password"));

            configuration.setProperty("hibernate.connection.url", dbUrl);
            configuration.setProperty("hibernate.connection.username", dbUser);
            configuration.setProperty("hibernate.connection.password", dbPassword);
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            log.info("Database URL: {}", dbUrl);
            log.info("Database User: {}", dbUser);
            log.info("Using environment: {}", System.getenv("DB_URL") != null ? "DOCKER" : "LOCAL");

            configuration.addAnnotatedClass(Answer.class);
            log.info("Added annotated class {}", Answer.class);
            configuration.addAnnotatedClass(Quest.class);
            log.info("Added annotated class {}", Quest.class);
            configuration.addAnnotatedClass(Question.class);
            log.info("Added annotated class {}", Question.class);
            configuration.addAnnotatedClass(User.class);
            log.info("Added annotated class {}", User.class);

            configuration.addAnnotatedClass(UserQuest.class);
            log.info("Added annotated class {}", UserQuest.class);

            sessionFactory = configuration.buildSessionFactory();
            log.info("SessionFactory created successfully");

        } catch (Exception e) {
            log.error("Failed to create SessionFactory", e);
            throw new RuntimeException(e);
        }
    }

    //for tests (Liquibase)
    private SessionCreator(Properties properties) {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.url", properties.getProperty("hibernate.connection.url"));
            configuration.setProperty("hibernate.connection.username", properties.getProperty("hibernate.connection.username"));
            configuration.setProperty("hibernate.connection.password", properties.getProperty("hibernate.connection.password"));
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            configuration.addAnnotatedClass(Answer.class);
            configuration.addAnnotatedClass(Quest.class);
            configuration.addAnnotatedClass(Question.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(UserQuest.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = SessionCreator
                .class.getClassLoader().getResourceAsStream("hibernate.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
                log.info("Properties file hibernate.properties loaded successfully");
            } else {
                log.warn("Properties file hibernate.properties not found");
            }
        } catch (IOException e) {
            log.error("Failed to load hibernate.properties", e);
            throw new RuntimeException(e);
        }
        return properties;
    }

    private String getEnvOrDefault(String env, String defaultValue) {
        String value = System.getenv(env);
        if (value != null && !value.trim().isEmpty()) {
            log.info("Using environment variable {} = {}", env, env.contains("PASSWORD") ? "****" : value);
            return value;
        } else {
            log.info("Using default value for {} = {}", env, env.contains("PASSWORD") ? "****" : defaultValue);
            return defaultValue;
        }
    }

    public Session getSession() {
        log.info("Creating Session");
        return sessionFactory.openSession();
    }

    @Override
    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            log.info("SessionFactory closed successfully");
        }
    }
}
