package com.dsrm.dsrmbackend;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletContext;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@WebAppConfiguration
@AutoConfigureMockMvc
class UserControllerIntegrationTests extends  AbstractIntegrationTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Test
    void checkUserControllerExistAndContextWorkProperly() {
        ServletContext servletContext = context.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);

    }

    @Test
    void retrieveNonExistingUser() throws Exception {
        this.mockMvc
                .perform(get("/users/1000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void retrieveExistingUser() throws Exception {
        this.mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$.email", equalTo("test01@wp.pl")))
        .andExpect(jsonPath("$.name", equalTo("Jan")))
        .andExpect(jsonPath("$.surname", equalTo("Kowalski")))
        .andExpect(status().isOk())
        .andDo(print());

    }

    @Test
    void addValidUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequestDTO userRequestDTO  = new UserRequestDTO();
        userRequestDTO.setName("Jan");
        userRequestDTO.setEmail("Jan@gmail.com");
        userRequestDTO.setSurname("Chraboszcz");
        userRequestDTO.setPassword("Marciniak");
        userRequestDTO.setRoles(null);
        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void retrieveUsersInRange() throws Exception {
        this.mockMvc.perform(get("/users?page=0%size=2")
                 .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Jan"))
                .andExpect(jsonPath("$.content[1].name").value("Piotr"));
    }

}