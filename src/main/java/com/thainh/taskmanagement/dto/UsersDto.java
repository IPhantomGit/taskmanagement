package com.thainh.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Users",
        description = "Users information"
)
public class UsersDto {

    @Schema(description = "user id", example = "1234567890")
    private Long id;
    @Schema(description = "user name", example = "thainh")
    @NotEmpty(message = "Username is required")
    @Size(min=5, max= 10, message = "Username should be between 5 and 10 characters")
    private String username;
    @Schema(description = "user full name", example = "Nguyen Thai")
    @NotEmpty(message = "Full name is required")
    @Size(min=5, message = "Full name must be at least 5 characters")
    private String fullName;
}
