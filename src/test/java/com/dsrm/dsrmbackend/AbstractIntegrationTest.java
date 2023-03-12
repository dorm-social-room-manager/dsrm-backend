package com.dsrm.dsrmbackend;


import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;


public abstract class AbstractIntegrationTest{


    @Container
    public static final MariaDBContainer<?> container = new MariaDBContainer<>("mariadb:10")
            .withDatabaseName("user-test")
            .withUsername("1234")
            .withPassword("1234")
            .withInitScript("dsrm_database.sql");

    public static class DatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword(),
                    "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true",
                    "spring.jpa.hibernate.ddl-auto=update"
            ).applyTo(applicationContext.getEnvironment());
        }
    }

}
