package com.thainh.taskmanagement.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thainh.taskmanagement.dto.ResponseDto;
import com.thainh.taskmanagement.dto.SearchDto;
import com.thainh.taskmanagement.dto.TaskListDto;
import com.thainh.taskmanagement.service.ITaskService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Search Task",
        description = "Search APIs"
)
@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
@Validated
public class SearchController {

    private ITaskService taskService;

    @Operation(
            summary = "Filter task by conditions",
            description = "Filter task by conditions"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Create successfully"
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
                                            {\
                                                "apiPath": "uri=/api/users/delete/12",
                                                "errorCode": "NOT_FOUND",
                                                "errorMessage": "User not found with field id : '12'",
                                                "errorTime": "2025-05-22T22:08:11.8181917"
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
                                            {  "apiPath": "uri=/api/search/",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
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
