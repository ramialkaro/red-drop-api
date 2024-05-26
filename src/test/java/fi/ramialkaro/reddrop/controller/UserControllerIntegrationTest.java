package fi.ramialkaro.reddrop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.ramialkaro.reddrop.model.User;
import fi.ramialkaro.reddrop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user1 = createMockUser(1L, "tester", "tester", "test@tester.fi", "tester", "tester");
        User user2 = createMockUser(2L, "tester2", "tester2", "test2@tester.fi", "tester2", "tester2");

        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);
        when(userService.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userService.getUserById(2L)).thenReturn(Optional.of(user2));
    }

    private User createMockUser(Long id, String username, String password, String email, String firstName,
            String lastName) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    @Test
    public void getAllUsersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void testGetUserById_UserExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("tester"))
                .andExpect(jsonPath("$.password").value("tester"))
                .andExpect(jsonPath("$.email").value("test@tester.fi"))
                .andExpect(jsonPath("$.firstName").value("tester"))
                .andExpect(jsonPath("$.lastName").value("tester"));
    }

    @Test
    void testGetUserById_UserDoesNotExist() throws Exception {
        when(userService.getUserById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        User user = createMockUser(1L, "tester", "tester", "test@tester.fi", "tester", "tester");
        when(userService.createUser(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
