package com.thainh.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(
        name = "Feature category",
        description = "Feature information"
)
public class FeatureDto extends TaskDto {

    @Schema(description = "business value", example = "create a new feature")
    private String businessValue;

    @NotNull(message = "Deadline is required")
    @Pattern(regexp = "^\\d{4}/\\d{2}/\\d{2}$", message = "Deadline must be in yyyy/MM/dd format")
    @Schema(description = "Deadline", example = "2025/05/23")
    private String deadline;
}
