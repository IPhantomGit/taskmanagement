package com.thainh.taskmanagement.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thainh.taskmanagement.dto.SearchDto;
import com.thainh.taskmanagement.dto.TaskListDto;
import com.thainh.taskmanagement.entity.Bug;
import com.thainh.taskmanagement.entity.Task;
import com.thainh.taskmanagement.entity.Users;
import com.thainh.taskmanagement.repository.BugRepository;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskRepository taskRepository;

    @MockitoBean
    private BugRepository bugRepository;

    @MockitoBean
    private UsersRepository usersRepository;

    @Test
    @DisplayName("Search task successfully")
    public void testSearchTask() throws Exception {
        // Mock a paginated response
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto searchDto = new SearchDto();
        searchDto.setPageNum(0);
        searchDto.setPageSize(10);
        searchDto.setStatus(0);
        searchDto.setUserId(1L);
        searchDto.setTitle("Test");
        searchDto.setDescription("description");
        String json = objectMapper.writeValueAsString(searchDto);

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setStatus(0);
        task.setCategory(0);
        task.setCategoryId(1L);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());
        Bug bug = new Bug();
        bug.setId(1L);
        bug.setSeverity(0);
        bug.setStepsToReproduce("Steps to reproduce");
        bug.setExpectedResult("Expected result");
        bug.setActualResult("Actual result");

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");

        Page<Task> page = new PageImpl<>(List.of(task), PageRequest.of(0, 10), 1);
        when(taskRepository.searchTasks(anyInt(), anyLong(), anyString(), anyString(), any())).thenReturn(page);
        when(bugRepository.findById(1L)).thenReturn(Optional.of(bug));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        mockMvc.perform(post("/api/search/")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<TaskListDto>() {});
                    assertEquals("fetch all tasks successful",
                            "Test Task",
                            actual.getResults().getFirst().get("title").asText());
                });
    }
}
