package com.dsrm.dsrmbackend;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

@SpringBootTest
class DsrmBackendApplicationTests {

    private static final DockerImageName MARIADB_IMAGE = DockerImageName.parse("mariadb:latest");
    private static final MariaDBContainer<?> mariadb;

    static {
        mariadb = new MariaDBContainer<>(MARIADB_IMAGE)
                .withCommand("--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci");
        mariadb.start();
    }
    @Test
    void testBasicContainer(){


    }

}
