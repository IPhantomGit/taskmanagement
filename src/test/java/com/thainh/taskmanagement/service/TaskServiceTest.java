package com.thainh.taskmanagement.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.BugDto;
import com.thainh.taskmanagement.dto.FeatureDto;
import com.thainh.taskmanagement.entity.Bug;
import com.thainh.taskmanagement.entity.Feature;
import com.thainh.taskmanagement.entity.Task;
import com.thainh.taskmanagement.entity.Users;
import com.thainh.taskmanagement.repository.BugRepository;
import com.thainh.taskmanagement.repository.FeatureRepository;
import com.thainh.taskmanagement.repository.TaskRepository;
import com.thainh.taskmanagement.repository.UsersRepository;
import com.thainh.taskmanagement.service.impl.TaskServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BugRepository bugRepository;

    @Mock
    private FeatureRepository featureRepository;

    @Mock
    private Validator validator;

    @Test
    @DisplayName("Get all tasks successfully")
    public void testFetchAllTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(0);
        task.setUserId(1L);
        task.setCategoryId(1L);
        task.setCategory(0);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());
        Bug bug = new Bug();
        bug.setId(1L);
        bug.setSeverity(0);

        BugDto bugDto = new BugDto();
        bugDto.setId(1L);
        bugDto.setSeverity(0);
        bugDto.setStepsToReproduce("Steps to reproduce");
        bugDto.setExpectedResult("Expected result");
        bugDto.setActualResult("Actual result");
        bugDto.setStatus(0);
        bugDto.setCategory(0);
        bugDto.setCategoryId(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.valueToTree(bugDto);

        Page<Task> tasks = new PageImpl<>(List.of(task), PageRequest.of(0, 10), 1L);
        when(taskRepository.findAllByOrderByCreatedAtDesc(any())).thenReturn(tasks);
        when(bugRepository.findById(1L)).thenReturn(Optional.of(bug));
        when(mapper.valueToTree(any())).thenReturn(json);

        var res = taskService.fetchAllTasks();
        assertNotNull(res);
        assertEquals(0,res.getContent().get(0).get("category").asInt(),
                "First task category should be 0");
    }

    @Test
    @DisplayName("Update task successfully, update severity of bug")
    public void testUpdateTask1() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(0);
        task.setUserId(1L);
        task.setCategoryId(1L);
        task.setCategory(0);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());
        Bug bug = new Bug();
        bug.setId(1L);
        bug.setSeverity(0);

        BugDto bugDto = new BugDto();
        bugDto.setId(1L);
        bugDto.setUserId(1L);
        bugDto.setSeverity(1);
        bugDto.setStepsToReproduce("Steps to reproduce");
        bugDto.setExpectedResult("Expected result");
        bugDto.setActualResult("Actual result");
        bugDto.setStatus(0);
        bugDto.setCategory(0);
        bugDto.setCategoryId(1L);

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.valueToTree(bugDto);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        when(mapper.convertValue(json, BugDto.class)).thenReturn(bugDto);
        when(validator.validate(any())).thenReturn(Set.of());

        taskService.updateTask(1L, json);
        ArgumentCaptor<Bug> captorBug = ArgumentCaptor.forClass(Bug.class);
        verify(bugRepository, times(1)).save(captorBug.capture());
        assertEquals(1, captorBug.getValue().getSeverity(), "update severity of bug");
        ArgumentCaptor<Task> captorTask = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(captorTask.capture());
        assertEquals(1L, captorTask.getValue().getCategoryId(), "update same bug");
    }

    @Test
    @DisplayName("Update task successfully, update cateogry of task")
    public void testUpdateTask2() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(0);
        task.setUserId(1L);
        task.setCategoryId(1L);
        task.setCategory(0);
        task.setCreatedAt(LocalDate.parse("2025/05/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay());
        Bug bug = new Bug();
        bug.setId(1L);
        bug.setSeverity(0);

        BugDto bugDto = new BugDto();
        bugDto.setId(1L);
        bugDto.setUserId(1L);
        bugDto.setSeverity(1);
        bugDto.setStepsToReproduce("Steps to reproduce");
        bugDto.setExpectedResult("Expected result");
        bugDto.setActualResult("Actual result");
        bugDto.setStatus(0);
        bugDto.setCategory(0);
        bugDto.setCategoryId(1L);

        FeatureDto featureDto = new FeatureDto();
        featureDto.setId(1L);
        featureDto.setUserId(1L);
        featureDto.setBusinessValue("new feature");
        featureDto.setDeadline("2025/05/24");
        featureDto.setTitle("Title");
        featureDto.setDescription("Description");
        featureDto.setStatus(0);
        featureDto.setCategory(1);
        featureDto.setCategoryId(1L);

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.valueToTree(featureDto);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        when(bugRepository.findById(1L)).thenReturn(Optional.of(bug));
        when(mapper.convertValue(json, FeatureDto.class)).thenReturn(featureDto);
        when(validator.validate(any())).thenReturn(Set.of());

        LocalDate mockDate = LocalDate.parse("2025/05/20", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        LocalDate mockDeadline = LocalDate.parse("2025/05/24", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        try (MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class)) {
            mockedStatic.when(LocalDate::now).thenReturn(mockDate);
            mockedStatic.when(() -> LocalDate.parse(anyString(), any())).thenReturn(mockDeadline);
            taskService.updateTask(1L, json);

            verify(bugRepository, times(1)).delete(any());
            ArgumentCaptor<Feature> captorFeature = ArgumentCaptor.forClass(Feature.class);
            verify(featureRepository, times(1)).save(captorFeature.capture());
            assertEquals("new feature", captorFeature.getValue().getBusinessValue(), "create new feature");
            ArgumentCaptor<Task> captorTask = ArgumentCaptor.forClass(Task.class);
            verify(taskRepository, times(1)).save(captorTask.capture());
            assertEquals(1, captorTask.getValue().getCategory(), "update category");
        }

    }
}
