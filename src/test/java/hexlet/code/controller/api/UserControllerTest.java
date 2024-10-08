package hexlet.code.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private User testUser;
    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @Autowired
    private ModelGenerator modelGenerator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteById(testUser.getId());
    }

    @Test
    public void testIndex() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users")
                        .with(jwt())
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains(testUser.getEmail());
    }

    @Test
    public void testShow() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users" + "/{id}", testUser.getId())
                        .with(jwt())
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains(testUser.getEmail(), testUser.getFirstName());
    }

    @Test
    public void testCreate() throws Exception {
        User createUser = Instancio.of(modelGenerator.getUserModel()).create();

        mockMvc.perform(
                post("/api/users")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUser))
        ).andExpect(status().isCreated());

        User userFromRepo = userRepository.findByEmail(createUser.getEmail()).get();

        assertNotNull(userFromRepo);
        assertThat(createUser.getFirstName()).isEqualTo(userFromRepo.getFirstName());
        assertThat(createUser.getEmail()).isEqualTo(userFromRepo.getEmail());
    }

    @Test
    public void testUpdate() throws Exception {
        Map<String, String> updateData = new HashMap<>() {{
                put("firstName", "Kris");
                put("email", "test@example.com");
            }};

        mockMvc.perform(
                put("/api/users" + "/{id}", testUser.getId())
                        .with(jwt().jwt(builder -> builder.subject(testUser.getEmail())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData))
        ).andExpect(status().isOk());

        User updatedUser = userRepository.findById(testUser.getId()).get();
        assertThat(updatedUser.getFirstName()).isEqualTo("Kris");
        assertThat(updatedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(
                delete("/api/users" + "/{id}", testUser.getId())
                        .with(jwt().jwt(builder -> builder.subject(testUser.getEmail())))
        ).andExpect(status().isNoContent());

        assertTrue(userRepository.findById(testUser.getId()).isEmpty());
    }
}
