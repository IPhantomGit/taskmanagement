package com.thainh.taskmanagement.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.*;
import com.thainh.taskmanagement.service.ITaskService;
import com.thainh.taskmanagement.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Task Management",
        description = "Task Management APIs"
)
@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
@Validated
public class TaskController {

    private ITaskService taskService;

    @Operation(
            summary = "Create a task",
            description = "Create a task"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Create successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    summary = "Not valid fields",
                                    value = """
                                            {
                                                "category": "Category must be between 0 or 1",
                                                "status": "Status must be between 0 and 2"
                                            }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "InternalServerErrorExample",
                                    summary = "Error unknown",
                                    value = """
                                            {  "apiPath": "uri=/api/tasks/create",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> createTask(@Valid @RequestBody ObjectNode taskDto) {
        taskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(Constants.STATUS_201,Constants.CREATE_SUCCESS));
    }

    @Operation(
            summary = "Get all tasks",
            description = "Get all tasks"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetch all tasks"
            )}
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskListDto> fetchAllTasks() {
        Page<ObjectNode> tasks = taskService.fetchAllTasks();
        TaskListDto result = new TaskListDto(tasks.getContent(),
                tasks.getTotalElements(),
                tasks.getTotalPages());
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Fetch a task",
            description = "Fetch a task by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetch successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    summary = "NOT FOUND",
                                    value = """
                                            {
                                                "apiPath": "uri=/api/tasks/1",
                                                "errorCode": "NOT_FOUND",
                                                "errorMessage": "Task not found with field id : '1'",
                                                "errorTime": "2025-05-23T23:49:35.8741427"
                                            }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "InternalServerErrorExample",
                                    summary = "Error unknown",
                                    value = """
                                            {  "apiPath": "uri=/api/tasks/1",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> fetchTask(@PathVariable("id") Long id) {
        ObjectNode objectNode = taskService.fetchTaskById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectNode);
    }

    @Operation(
            summary = "Delete an user",
            description = "Delete an user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    summary = "NOT FOUND",
                                    value = """
                                            {
                                                "apiPath": "uri=/api/tasks/delete/1",
                                                "errorCode": "NOT_FOUND",
                                                "errorMessage": "Task not found with field id : '1'",
                                                "errorTime": "2025-05-23T23:49:35.8741427"
                                            }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "InternalServerErrorExample",
                                    summary = "Error unknown",
                                    value = """
                                            {  "apiPath": "uri=/api/tasks/delete",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(Constants.STATUS_200,Constants.DELETE_SUCCESS));
    }

    @Operation(
            summary = "Update a task",
            description = "Update a task by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Update successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    summary = "BAD REQUEST",
                                    value = """
                                            {\
                                                "apiPath": "uri=/api/tasks/update/1",
                                                "errorCode": "BAD_REQUEST",
                                                "errorMessage": "Nothing to update",
                                                "errorTime": "2025-05-22T21:35:39.7374659"
                                            }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "InternalServerErrorExample",
                                    summary = "Error unknown",
                                    value = """
                                            {  "apiPath": "uri=/api/tasks/update/1",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PatchMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> updateTask(@PathVariable("id") Long id,
                                                  @RequestBody ObjectNode objectNode) {
        taskService.updateTask(id, objectNode);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(Constants.STATUS_200,Constants.UPDATE_SUCCESS));
    }
}
