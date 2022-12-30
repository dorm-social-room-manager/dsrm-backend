package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.controllers.RoomTypeController;
import com.dsrm.dsrmbackend.dto.RoomTypeDTO;
import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import lombok.RequiredArgsConstructor;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MariaDBContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
@ContextConfiguration(initializers = {RoomTypeControllerTests.Initializer.class})
public class RoomTypeControllerTests {
    @Autowired
    private RoomTypeController controller;
    @ClassRule
    public static MariaDBContainer container = new MariaDBContainer<>("mariadb:10")
            .withDatabaseName("room-test")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("dsrm_database.sql");

    // Set configuration for test MariaDB Container
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword(),
                    "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true"
            ).applyTo(context.getEnvironment());
        }
    }

    @Test
    public void addRoomType() {
        RoomTypeRequestDTO roomTypeRequestDTO = new RoomTypeRequestDTO("Ping-Pong");
        RoomType roomType = controller.addRoomType(roomTypeRequestDTO);
        assertNotNull(roomType);
    }

    @Test
    public void getExistentRoomType() {
        ResponseEntity<RoomTypeDTO> response = controller.getRoomType(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        RoomTypeDTO roomType = response.getBody();
        assertNotNull(roomType);
        assertEquals("Pokoj mieszkalny", roomType.getName());
    }

    @Test
    public void tryToGetNonexistentRoomType() {
        ResponseEntity<RoomTypeDTO> response = controller.getRoomType(5124L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        RoomTypeDTO roomType = response.getBody();
        assertNull(roomType);
    }

    @Test
    public void getRoomTypesInRange() {
        Pageable pageable = PageRequest.of(0,2);
        ResponseEntity<Page<RoomTypeDTO>> response = controller.readRoomTypes(pageable);
        Page<RoomTypeDTO> page = response.getBody();
        assertNotNull(page);
        List<RoomTypeDTO> rooms = page.getContent();
        assertEquals(2, rooms.size());
    }

}
