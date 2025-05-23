package com.thainh.taskmanagement.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.SearchDto;
import com.thainh.taskmanagement.dto.TaskListDto;
import com.thainh.taskmanagement.service.ITaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Task Management",
        description = "Task Management APIs"
)
@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
@Validated
public class SearchController {

    private ITaskService taskService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskListDto> search(@Valid @RequestBody SearchDto searchDto) {
        Page<ObjectNode> result = taskService.searchTasks(searchDto);
        TaskListDto taskListDto = new TaskListDto(
                result.getContent(),
                result.getTotalElements(),
                result.getTotalPages());
        return ResponseEntity.ok(taskListDto);
    }
}
