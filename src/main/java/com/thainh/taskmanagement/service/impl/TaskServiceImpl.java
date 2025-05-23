package com.thainh.taskmanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.BugDto;
import com.thainh.taskmanagement.dto.FeatureDto;
import com.thainh.taskmanagement.entity.Bug;
import com.thainh.taskmanagement.entity.Feature;
import com.thainh.taskmanagement.entity.Task;
import com.thainh.taskmanagement.exception.ResourceNotFoundException;
import com.thainh.taskmanagement.exception.TaskRequestException;
import com.thainh.taskmanagement.mapper.TaskMapper;
import com.thainh.taskmanagement.repository.BugRepository;
import com.thainh.taskmanagement.repository.FeatureRepository;
import com.thainh.taskmanagement.repository.TaskRepository;
import com.thainh.taskmanagement.repository.UsersRepository;
import com.thainh.taskmanagement.service.ITaskService;
import com.thainh.taskmanagement.utils.Constants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final FeatureRepository featureRepository;
    private TaskRepository taskRepository;
    private BugRepository bugRepository;
    private UsersRepository usersRepository;
    private Validator validator;

    private static final String CATEGORY = "category";

    @Override
    public List<ObjectNode> fetchAllTasks() {
        List<Task> tasks = taskRepository.findAllByOrderByCreatedAtDesc();
        List<Bug> bugs = bugRepository.findAll();
        List<Feature> features = featureRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        if (!tasks.isEmpty()) {
            return tasks.stream().map(task -> {
                if (Constants.CATEGORY.BUG.getCode() == task.getCategory()) {
                    Bug bug = bugs.stream().filter(b -> Objects.equals(b.getId(), task.getCategoryId()))
                            .findFirst().orElse(null);
                    if (bug != null) {
                        BugDto bugDto = TaskMapper.mapToBugDto(bug, task, new BugDto());
                        return mapper.valueToTree(bugDto);
                    }
                } else {
                    Feature feature = features.stream().filter(b -> Objects.equals(b.getId(), task.getCategoryId()))
                            .findFirst().orElse(null);
                    if (feature != null) {
                        FeatureDto featureDto = TaskMapper.mapToFeatureDto(feature, task, new FeatureDto());
                        return (ObjectNode) mapper.valueToTree(featureDto);
                    }
                }
                return null;
            }).filter(Objects::nonNull).toList();
        }
        return List.of();
    }

    @Override
    public void createTask(ObjectNode taskDto) {
        // check if user is valid
        Long userId = taskDto.get("userId").asLong();
        usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", "" + userId));
        int category = taskDto.get(CATEGORY).asInt();
        ObjectMapper objectMapper = new ObjectMapper();
        // check category
        if (Constants.CATEGORY.BUG.getCode() == category) {
            BugDto bugDto = objectMapper.convertValue(taskDto, BugDto.class);
            Set<ConstraintViolation<BugDto>> violations = validator.validate(bugDto);
            if (!violations.isEmpty()) {
                List<String> invalidFields = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .toList();
                throw new TaskRequestException("%s%s", "", String.join(", ", invalidFields));
            }
            createBug(bugDto);
        } else if (Constants.CATEGORY.FEATURE.getCode() == category) {
            FeatureDto featureDto = objectMapper.convertValue(taskDto, FeatureDto.class);
            Set<ConstraintViolation<FeatureDto>> violations = validator.validate(featureDto);
            if (!violations.isEmpty()) {
                List<String> invalidFields = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .toList();
                throw new TaskRequestException("%s%s", "", String.join(", ", invalidFields));
            }
            createFeature(featureDto);
        } else {
            throw new TaskRequestException("Invalid category%s: [%s]", "", "" + category);
        }
    }

    private void createBug(BugDto bugDto) {
        Bug bug = TaskMapper.mapToBug(bugDto, new Bug());
        bugRepository.save(bug);
        bugDto.setCategoryId(bug.getId());
        Task task = TaskMapper.mapToTask(bugDto, new Task());
        taskRepository.save(task);
    }

    private void createFeature(FeatureDto featureDto) {
        Feature feature;
        try {
            feature = TaskMapper.mapToFeature(featureDto, new Feature());
            if (feature.getDeadline().isBefore(LocalDate.now())) {
                throw new TaskRequestException("Validation %s failed: %s", "deadline", "Deadline must be in the future");
            }
        } catch (ParseException e) {
            throw new TaskRequestException("Validation %s failed: %s", "deadline", e.getMessage());
        }
        featureRepository.save(feature);
        featureDto.setCategoryId(feature.getId());
        Task task = TaskMapper.mapToTask(featureDto, new Task());
        taskRepository.save(task);
    }
}

