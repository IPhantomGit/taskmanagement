package com.thainh.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
    name = "Response",
    description = "Successful Response"
)
public class ResponseDto {

    @Schema(description = "status code", example = "200")
    private String statusCode;

    @Schema(description = "status message", example = "Success")
    private String statusMessage;
}
