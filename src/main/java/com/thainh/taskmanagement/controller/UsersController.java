package com.thainh.taskmanagement.controller;

import com.thainh.taskmanagement.dto.ResponseDto;
import com.thainh.taskmanagement.dto.UsersDto;
import com.thainh.taskmanagement.dto.UsersListDto;
import com.thainh.taskmanagement.service.IUsersService;
import com.thainh.taskmanagement.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersListDto> fetchAllUsers() {
        UsersListDto result = new UsersListDto(usersService.fetchAllUsers());
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Create an user",
            description = "Create an user"
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
                                    summary = "BAD REQUEST",
                                    value = """
                                            {\
                                                "apiPath": "uri=/api/users/create",
                                                "errorCode": "BAD_REQUEST",
                                                "errorMessage": "Username [thainh] already exists",
                                                "errorTime": "2025-05-22T21:35:39.7374659"
                                            }"""
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
                                    value = """
                                            {  "apiPath": "uri=/api/users/create",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UsersDto usersDto) {
        usersService.createUser(usersDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(Constants.STATUS_201,Constants.CREATE_SUCCESS));
    }

    @Operation(
            summary = "Update an user",
            description = "Update an user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Update successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    summary = "BAD REQUEST",
                                    value = """
                                            {\
                                                "apiPath": "uri=/api/users/create",
                                                "errorCode": "BAD_REQUEST",
                                                "errorMessage": "Nothing to update",
                                                "errorTime": "2025-05-22T21:35:39.7374659"
                                            }"""
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
                                    value = """
                                            {  "apiPath": "uri=/api/users/update/1",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PatchMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> updateUser(@PathVariable("id") Long id,
                                                  @RequestBody UsersDto usersDto) {
        usersService.updateUser(id, usersDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(Constants.STATUS_200,Constants.UPDATE_SUCCESS));
    }

    @Operation(
            summary = "Fetch an user",
            description = "Fetch an user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetch successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    summary = "NOT FOUND",
                                    value = """
                                            {\
                                                "apiPath": "uri=/api/users/12",
                                                "errorCode": "NOT_FOUND",
                                                "errorMessage": "User not found with field id : '12'",
                                                "errorTime": "2025-05-22T22:08:11.8181917"
                                            }"""
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
                                    value = """
                                            {  "apiPath": "uri=/api/users/1",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersDto> fetchUser(@PathVariable("id") Long id) {
        UsersDto usersDto = usersService.fetchUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(usersDto);
    }

    @Operation(
            summary = "Delete an user",
            description = "Delete an user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    summary = "NOT FOUND",
                                    value = """
                                            {\
                                                "apiPath": "uri=/api/users/delete/12",
                                                "errorCode": "NOT_FOUND",
                                                "errorMessage": "User not found with field id : '12'",
                                                "errorTime": "2025-05-22T22:08:11.8181917"
                                            }"""
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
                                    value = """
                                            {  "apiPath": "uri=/api/users/delete/12",
                                                "errorCode": "INTERNAL_SERVER_ERROR",
                                                "errorMessage": "error unknown",
                                                "errorTime": "2025-05-22T01:49:48.0263839" }"""
                            ),
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable("id") Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(Constants.STATUS_200,Constants.DELETE_SUCCESS));
    }
}
