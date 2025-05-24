package com.thainh.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
        name = "Task",
        description = "Task information"
)
public class TaskDto {
    @Schema(description = "task id", example = "1234567890")
    private Long id;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title must not be empty")
    @Schema(description = "task title", example = "New task")
    private String title;
    @Schema(description = "task description", example = "description")
    private String description;
    @NotNull
    @Min(value = 0, message = "User id must be greater than 0")
    @Schema(description = "assignee", example = "1")
    private Long userId;
    @NotNull
    @Min(value = 0, message = "Category must be between 0 or 1")
    @Max(value = 1, message = "Category must be between 0 or 1")
    @Schema(description = "task category, using enum from Constants", example = "0")
    private int category;
    @Schema(description = "category id", example = "1")
    private Long categoryId;
    @NotNull
    @Min(value = 0, message = "Status must be between 0 and 2")
    @Max(value = 2, message = "Status must be between 0 and 2")
    @Schema(description = "status, using enum from Constants", example = "0")
    private int status;
    @Schema(description = "createAt", example = "2025/05/23")
    private String createdAt;

}
