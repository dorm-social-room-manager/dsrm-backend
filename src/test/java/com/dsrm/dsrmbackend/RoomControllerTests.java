package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.controllers.RoomController;
import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
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

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
@ContextConfiguration(initializers = {RoomControllerTests.Initializer.class})
public class RoomControllerTests {

    @Autowired
    private RoomController controller;
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
    public void insertValidRoom() {
        RoomRequestDTO roomRequestDTO = new RoomRequestDTO("test", 203, 2, 3,
                6, LocalTime.now(), LocalTime.now());
        Room room = controller.addRoom(roomRequestDTO);
        assertNotNull(room);
        assertEquals(203, room.getRoomNumber());
    }

    @Test
    public void getExististentRoom() {
        ResponseEntity<RoomDTO> response = controller.getRoom(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        RoomDTO room = response.getBody();
        assertNotNull(room);
        assertEquals(111, room.getRoomNumber());
    }

    @Test
    public void tryToGetNonexistentRoom() {
        ResponseEntity<RoomDTO> response = controller.getRoom(5124L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        RoomDTO room = response.getBody();
        assertNull(room);
    }

    @Test
    public void getRoomsInRange(){
        Pageable pageable = PageRequest.of(0,2);
        ResponseEntity<Page<RoomDTO>> response = controller.readRooms(pageable);
        Page<RoomDTO> page = response.getBody();
        assertNotNull(page);
        List<RoomDTO> rooms = page.getContent();
        assertEquals(2, rooms.size());
    }
}
