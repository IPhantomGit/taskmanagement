package com.thainh.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Schema(
    name = "UsersList",
    description = "Users list information"
)
@AllArgsConstructor
public class UsersListDto {
    @Schema(description = "list of users", example = "[]")
    private List<UsersDto> users;
}
