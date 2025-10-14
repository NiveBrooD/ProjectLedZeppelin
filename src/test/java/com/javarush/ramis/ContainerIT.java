package com.javarush.ramis;

import com.javarush.ramis.config.Liquibase;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

public class ContainerIT {

    private final static PostgreSQLContainer<?> CONTAINER;

    public static final String DOCKER_IMAGE_NAME = "postgres:16.3";

    public static final Properties PROPERTIES = new Properties();

    static {
        CONTAINER = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);
        CONTAINER.start();

        PROPERTIES.setProperty("hibernate.connection.username", CONTAINER.getUsername());
        PROPERTIES.setProperty("hibernate.connection.password", CONTAINER.getPassword());
        PROPERTIES.setProperty("hibernate.connection.url", CONTAINER.getJdbcUrl());
        PROPERTIES.setProperty("hibernate.connection.driver_class", CONTAINER.getDriverClassName());

        new Liquibase(PROPERTIES).start();
    }
}
