package com.thainh.taskmanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.BugDto;
import com.thainh.taskmanagement.dto.TaskDto;
import com.thainh.taskmanagement.entity.Bug;
import com.thainh.taskmanagement.entity.Task;
import com.thainh.taskmanagement.mapper.TaskMapper;
import com.thainh.taskmanagement.repository.BugRepository;
import com.thainh.taskmanagement.repository.TaskRepository;
import com.thainh.taskmanagement.service.ITaskService;
import com.thainh.taskmanagement.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private TaskRepository taskRepository;
    private BugRepository bugRepository;

    private static final String CATEGORY = "category";

    @Override
    public void createTask(ObjectNode taskDto) {
        int category = taskDto.get(CATEGORY).asInt();
        ObjectMapper objectMapper = new ObjectMapper();
        if (Constants.CATEGORY.BUG.getCode() == category) {
            BugDto bugDto = objectMapper.convertValue(taskDto, BugDto.class);
            createBug(bugDto);
        } else if (Constants.CATEGORY.FEATURE.getCode() == category) {
            createFeature(taskDto);
        } else {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    private void createBug(BugDto bugDto) {
        Bug bug = TaskMapper.mapToBug(bugDto, new Bug());
        bugRepository.save(bug);
        bugDto.setCategoryId(bug.getId());
        Task task = TaskMapper.mapToTask(bugDto, new Task());
        taskRepository.save(task);
    }

    private void createFeature(ObjectNode taskDto) {
    }
}
