package com.thainh.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(
        name = "Bug category",
        description = "Bug information"
)
public class BugDto extends TaskDto{
    @Schema(description = "severity level", example = "1")
    private int severity;
    @Schema(description = "step to produce", example = "step1 step2 step3")
    private String stepsToReproduce;
    @Schema(description = "expected result", example = "expected")
    private String expectedResult;
    @Schema(description = "actual result", example = "actual")
    private String actualResult;
}
