package com.dsrm.dsrmbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ConfigurationPropertiesScan
public class DsrmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DsrmBackendApplication.class, args);
    }

}
