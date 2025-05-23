package com.thainh.taskmanagement.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ITaskService {

    void createTask(ObjectNode taskDto);

    List<ObjectNode> fetchAllTasks();

    ObjectNode fetchTaskById(Long id);
}
