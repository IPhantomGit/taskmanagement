package com.thainh.taskmanagement.dto;

import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class TaskListDto {
    @Schema(description = "list of users", example = "[]")
    private List<ObjectNode> results;
}
