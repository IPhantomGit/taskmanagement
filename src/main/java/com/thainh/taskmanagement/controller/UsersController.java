package com.thainh.taskmanagement.controller;

import com.thainh.taskmanagement.dto.ResponseDto;
import com.thainh.taskmanagement.dto.UsersDto;
import com.thainh.taskmanagement.dto.UsersListDto;
import com.thainh.taskmanagement.service.IUsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Users Management",
        description = "Users Management APIs"
)
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Validated
public class UsersController {

    private final IUsersService usersService;

    @Operation(
            summary = "Get all users",
            description = "Get all users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetch all users"
            )}
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersListDto> fetchAllUsers() {
        UsersListDto result = new UsersListDto(usersService.fetchAllUsers());
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Create a user",
            description = "Create all users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Create successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    summary = "Missing fields",
                                    value = "{ \"fullName\": \"Full name is required\", }"
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "InternalServerErrorExample",
                                    summary = "Error unknown",
                                    value = "{  \"apiPath\": \"uri=/api/users/create\",\n" +
                                            "    \"errorCode\": \"INTERNAL_SERVER_ERROR\",\n" +
                                            "    \"errorMessage\": \"error unknown\",\n" +
                                            "    \"errorTime\": \"2025-05-22T01:49:48.0263839\" }"
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UsersDto usersDto) {
        return ResponseEntity.ok(new ResponseDto("200", "Success"));
    }
}
