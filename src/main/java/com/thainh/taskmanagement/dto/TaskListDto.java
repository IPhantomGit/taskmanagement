package com.thainh.taskmanagement.dto;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema(
        name = "UsersList",
        description = "Users list information"
)
@NoArgsConstructor
@AllArgsConstructor
public class TaskListDto {
    @Schema(description = "list of users", example = "[]")
    private List<ObjectNode> results;
    @Schema(description = "number tasks in page", example = "0")
    private Long totalTask;
    @Schema(description = "page number", example = "0")
    private Integer totalPage;
}
