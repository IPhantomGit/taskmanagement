package com.thainh.taskmanagement.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.TaskDto;

public interface ITaskService {

    void createTask(ObjectNode taskDto);
}
