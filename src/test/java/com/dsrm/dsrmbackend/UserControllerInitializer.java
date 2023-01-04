package com.dsrm.dsrmbackend;


import com.dsrm.dsrmbackend.controllers.UserController;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MariaDBContainer;

public class UserControllerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @ClassRule
    public static MariaDBContainer container = new MariaDBContainer<>("mariadb:10")
            .withDatabaseName("user-test")
            .withUsername("1234")
            .withPassword("1234")
            .withInitScript("dsrm_database.sql");


    public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword(),
                    "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true"
            ).applyTo(applicationContext.getEnvironment());
    }
    static {
        container.start();
    }
}
