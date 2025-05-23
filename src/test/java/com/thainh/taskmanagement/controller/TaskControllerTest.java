package com.thainh.taskmanagement.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.BugDto;
import com.thainh.taskmanagement.dto.FeatureDto;
import com.thainh.taskmanagement.dto.TaskListDto;
import com.thainh.taskmanagement.entity.Bug;
import com.thainh.taskmanagement.entity.Feature;
import com.thainh.taskmanagement.entity.Task;
import com.thainh.taskmanagement.entity.Users;
import com.thainh.taskmanagement.repository.BugRepository;
import com.thainh.taskmanagement.repository.FeatureRepository;
import com.thainh.taskmanagement.repository.TaskRepository;
import com.thainh.taskmanagement.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskRepository taskRepository;

    @MockitoBean
    private BugRepository bugRepository;

    @MockitoBean
    private FeatureRepository featureRepository;

    @MockitoBean
    private UsersRepository usersRepository;

    @Test
    @DisplayName("Fetch all tasks successfully")
    public void testFetchAllTasks() throws Exception {
        // Mock a paginated response
        ObjectMapper objectMapper = new ObjectMapper();
        BugDto bugDto = new BugDto();
        bugDto.setId(1L);
        bugDto.setTitle("Test Task");
        bugDto.setSeverity(0);

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setCategory(0);
        task.setCategoryId(1L);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());
        Bug bug = new Bug();
        bug.setId(1L);
        bug.setSeverity(0);
        bug.setStepsToReproduce("Steps to reproduce");
        bug.setExpectedResult("Expected result");
        bug.setActualResult("Actual result");

        Page<Task> page = new PageImpl<>(List.of(task), PageRequest.of(0, 10), 1);
        when(taskRepository.findAllByOrderByCreatedAtDesc(any())).thenReturn(page);
        when(bugRepository.findById(1L)).thenReturn(Optional.of(bug));
        mockMvc.perform(get("/api/tasks/"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<TaskListDto>() {});
                    assertEquals("fetch all tasks successful",
                            bugDto.getSeverity(),
                            actual.getResults().getFirst().get("severity").asInt());
                });
    }

    @Test
    @DisplayName("Fetch a task by id successfully")
    public void testFetchTaskById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Task task = new Task();
        task.setId(1L);
        task.setCategory(1);
        task.setCategoryId(1L);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());

        FeatureDto featureDto = new FeatureDto();
        featureDto.setId(1L);
        featureDto.setTitle("Test Task");
        featureDto.setDeadline("2025/05/23");

        Feature feature = new Feature();
        feature.setId(1L);
        feature.setBusinessValue("Test feature");
        feature.setDeadline(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(featureRepository.findById(1L)).thenReturn(Optional.of(feature));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<ObjectNode>() {});
                    assertEquals("fetch all tasks successful",
                            featureDto.getDeadline(),
                            actual.get("deadline").asText());
                });
    }

    @Test
    @DisplayName("Delete a task successfully")
    public void testDeleteTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setCategory(1);
        task.setCategoryId(1L);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());

        Feature feature = new Feature();
        feature.setId(1L);
        feature.setBusinessValue("Test feature");
        feature.setDeadline(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(featureRepository.findById(1L)).thenReturn(Optional.of(feature));
        mockMvc.perform(delete("/api/tasks/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update a task successfully")
    public void testUpdateTask() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Task task = new Task();
        task.setId(1L);
        task.setCategory(1);
        task.setCategoryId(1L);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());

        Feature feature = new Feature();
        feature.setId(1L);
        feature.setBusinessValue("Test feature");
        feature.setDeadline(LocalDate.parse("2025/05/25", DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");

        FeatureDto featureDto = new FeatureDto();
        featureDto.setCategory(1);
        featureDto.setStatus(0);
        featureDto.setUserId(1L);
        featureDto.setTitle("New Task");
        featureDto.setDeadline("2025/05/25");
        String json = objectMapper.writeValueAsString(featureDto);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(featureRepository.findById(1L)).thenReturn(Optional.of(feature));

        mockMvc.perform(patch("/api/tasks/update/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<ObjectNode>() {});
                    assertEquals("fetch all tasks successful",
                            featureDto.getDeadline(),
                            actual.get("deadline").asText());
                });
    }

    @Test
    @DisplayName("Create task successfully")
    public void testCreateTask() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Task task = new Task();
        task.setId(1L);
        task.setCategory(1);
        task.setCategoryId(1L);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());

        Feature feature = new Feature();
        feature.setId(1L);
        feature.setBusinessValue("Test feature");
        feature.setDeadline(LocalDate.parse("2025/05/24", DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");

        FeatureDto featureDto = new FeatureDto();
        featureDto.setCategory(1);
        featureDto.setStatus(0);
        featureDto.setUserId(1L);
        featureDto.setTitle("New Task");
        featureDto.setDeadline("2025/05/24");
        String json = objectMapper.writeValueAsString(featureDto);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(featureRepository.findById(1L)).thenReturn(Optional.of(feature));

        mockMvc.perform(post("/api/tasks/create")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<ObjectNode>() {});
                    assertEquals("fetch all tasks successful",
                            featureDto.getDeadline(),
                            actual.get("deadline").asText());
                });
    }
}
