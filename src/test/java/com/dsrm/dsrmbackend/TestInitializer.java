package com.dsrm.dsrmbackend;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;

public class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Container
    public static MariaDBContainer<?> container = new MariaDBContainer<>("mariadb:10")
            .withDatabaseName("room-test")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("dsrm_database.sql");


    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of(
        "spring.datasource.url=" + container.getJdbcUrl(),
        "spring.datasource.username=" + container.getUsername(),
        "spring.datasource.password=" + container.getPassword(),
        "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true"
        ).applyTo(context.getEnvironment());
    }

    static {
        container.start();
    }
}