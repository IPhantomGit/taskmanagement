package com.thainh.taskmanagement.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.ResponseDto;
import com.thainh.taskmanagement.dto.TaskDto;
import com.thainh.taskmanagement.dto.UsersDto;
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
import org.springframework.http.HttpStatus;
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
                                            {  "apiPath": "uri=/api/users/create",
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
}
