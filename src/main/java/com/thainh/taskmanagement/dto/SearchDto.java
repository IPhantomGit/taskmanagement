package com.thainh.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
        name = "Search filter",
        description = "Search information"
)
public class SearchDto {

    @Min(value = 0, message = "Status must be between 0 and 2")
    @Max(value = 2, message = "Status must be between 0 and 2")
    @Schema(description = "status", example = "0")
    private Integer status;
    @Schema(description = "user id", example = "1")
    private Long userId;
    @Schema(description = "title contain text", example = "bug")
    private String title;
    @Schema(description = "description contain text", example = "description")
    private String description;
    @NotNull
    @Schema(description = "page number", example = "0")
    private Integer pageNum;
    @NotNull
    @Schema(description = "page size", example = "10")
    private Integer pageSize;
}
