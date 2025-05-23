package com.thainh.taskmanagement.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.SearchDto;
import org.springframework.data.domain.Page;

public interface ITaskService {

    void createTask(ObjectNode taskDto);

    Page<ObjectNode> fetchAllTasks();

    ObjectNode fetchTaskById(Long id);

    void deleteTask(Long id);

    void updateTask(Long id, ObjectNode objectNode);

    Page<ObjectNode> searchTasks(SearchDto searchDto);
}
