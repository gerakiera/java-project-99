package hexlet.code.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.ModelGenerator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;
    private Task testTask;
    private Task testTaskCreate;
    @Autowired
    private ModelGenerator modelGenerator;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LabelRepository labelRepository;

    @BeforeEach
    public void beforeEach() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        Label newLabel;
        Optional<Label> existingLabelOpt = labelRepository.findByName("example label");
        if (existingLabelOpt.isPresent()) {
            newLabel = existingLabelOpt.get();
        } else {
            newLabel = new Label();
            newLabel.setName("example label");
            labelRepository.save(newLabel);
        }
        TaskStatus status = new TaskStatus();
        status.setSlug("draft");
        status.setName("Draft");
        taskStatusRepository.save(status);

        testTask = modelGenerator.getTestTask();
        testTask.setTaskStatus(status);
        testTask.setLabels(Set.of(newLabel));
        taskRepository.save(testTask);
    }

    @AfterEach
    public void afterEach() {
        taskRepository.deleteById(testTask.getId());
        taskStatusRepository.deleteById(testTask.getTaskStatus().getId());
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(
                get("/api/tasks")
                        .with(token)
        ).andExpect(header().string("X-Total-Count", "1"));
    }

    @Test
    public void testShow() throws Exception {
        mockMvc.perform(
                get("/api/tasks/" + testTask.getId())
                        .with(token)
        ).andExpect(status().isOk());
        Task task = taskRepository.findById(testTask.getId()).get();
        assertThat(task.getAssignee()).isEqualTo(testTask.getAssignee());
        assertThat(task.getName()).isEqualTo(testTask.getName());
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        TaskCreateDTO taskToSave = new TaskCreateDTO();
        taskToSave.setTitle("New Task");
        taskToSave.setStatus("draft");

        mockMvc.perform(post("/api/tasks")
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskToSave)))
                .andExpect(status().isCreated());

        Task savedTask = taskRepository.findByName("New Task").orElseThrow();

        assertNotNull(savedTask);
        assertThat(savedTask.getName()).isEqualTo(taskToSave.getTitle());
        assertThat(savedTask.getTaskStatus().getSlug()).isEqualTo(taskToSave.getStatus());
    }

    @Test
    public void testUpdate() throws Exception {
        TaskUpdateDTO taskUpdate = new TaskUpdateDTO();
        taskUpdate.setTitle(JsonNullable.of("Updated Task"));

        mockMvc.perform(put("/api/tasks/" + testTask.getId())
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskUpdate)))
                .andExpect(status().isOk());

        Task updatedTask = taskRepository.findById(testTask.getId()).orElseThrow();
        assertEquals("Updated Task", updatedTask.getName());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/tasks/" + testTask.getId())
                        .with(token))
                .andExpect(status().isNoContent());
        assertFalse(taskRepository.findById(testTask.getId()).isPresent());
    }
}
