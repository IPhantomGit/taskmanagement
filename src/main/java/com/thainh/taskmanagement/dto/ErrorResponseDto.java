package com.thainh.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error response",
        description = "error response"
)
public class ErrorResponseDto {

    @Schema(description = "api path called by client")
    private String apiPath;

    @Schema(description = "error code")
    private HttpStatus errorCode;

    @Schema(description = "error message")
    private String errorMessage;

    @Schema(description = "error time")
    private LocalDateTime errorTime;

}
