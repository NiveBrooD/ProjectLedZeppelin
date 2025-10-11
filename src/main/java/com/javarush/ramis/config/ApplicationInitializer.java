package com.javarush.ramis.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class ApplicationInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Initializing Quest Application...");

        try {
            Liquibase liquibase = new Liquibase();
            liquibase.start();
            log.info("Database migrations completed successfully");

        } catch (Exception e) {
            log.error("Database migrations failed: {}", e.getMessage());
            throw new RuntimeException("Database initialization failed", e);
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Closing Quest Application...");
    }
}