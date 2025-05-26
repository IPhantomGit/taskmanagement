package com.thainh.taskmanagement.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thainh.taskmanagement.dto.UsersDto;
import com.thainh.taskmanagement.entity.Users;
import com.thainh.taskmanagement.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersRepository usersRepository;

    @Test
    @DisplayName("Fetch all user successfully")
    public void testFetchAllUser() throws Exception {
        UsersDto usersDto = new UsersDto();
        usersDto.setId(1L);
        usersDto.setUsername("thainh");
        usersDto.setFullName("Nguyen Thai");

        Users user = new Users();
        user.setId(1L);
        user.setUsername("thainh");
        user.setFullName("Nguyen Thai");

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();

        when(usersRepository.findAllByOrderByIdAscCreatedAtAsc()).thenReturn(List.of(user));

        // Perform request
        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<HashMap<String, List<UsersDto>>>() {});
                    assertEquals("fetch all user successful",
                            usersDto,
                            actual.get("results").get(0));
                });
    }

    @Test
    @DisplayName("Create user successfully")
    public void testCreateUser() throws Exception {
        UsersDto usersDto = new UsersDto();
        usersDto.setUsername("thainh");
        usersDto.setFullName("Nguyen Thai");

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(usersDto);

        var expectRes = new HashMap<>();
        expectRes.put("statusCode", "201");
        expectRes.put("statusMessage", "Created successfully");

        // Perform request
        mockMvc.perform(post("/api/users/create")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    var actual = result.getResponse().getContentAsString();
                    assertEquals("create user successful",
                            expectRes,
                            objectMapper.readValue(actual, new TypeReference<>() {}));
                });
    }

    @Test
    @DisplayName("Create user failed: user exist")
    public void testCreateUser1() throws Exception {
        UsersDto usersDto = new UsersDto();
        usersDto.setUsername("thainh");
        usersDto.setFullName("Nguyen Thai");

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        when(usersRepository.findByUsername("thainh")).thenReturn(Optional.of(users));

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(usersDto);

        var expectRes = new HashMap<>();
        expectRes.put("errorCode", "BAD_REQUEST");

        // Perform request
        mockMvc.perform(post("/api/users/create")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, String>>() {});
                    assertEquals("create user failed",
                            expectRes.get("errorCode"),
                            actual.get("errorCode"));
                });
    }

    @Test
    @DisplayName("Create user failed: exception error")
    public void testCreateUser2() throws Exception {
        UsersDto usersDto = new UsersDto();
        usersDto.setUsername("thainh");
        usersDto.setFullName("Nguyen Thai");

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        when(usersRepository.findByUsername("thainh")).thenThrow(new RuntimeException("failed"));

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(usersDto);

        var expectRes = new HashMap<>();
        expectRes.put("errorCode", "INTERNAL_SERVER_ERROR");

        // Perform request
        mockMvc.perform(post("/api/users/create")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, String>>() {});
                    assertEquals("create user failed",
                            expectRes.get("errorCode"),
                            actual.get("errorCode"));
                });
    }

    @Test
    @DisplayName("Create user failed: invalid user error")
    public void testCreateUser3() throws Exception {
        UsersDto usersDto = new UsersDto();
        usersDto.setUsername("thainh");
        usersDto.setFullName("NT");

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        when(usersRepository.findByUsername("thainh")).thenReturn(Optional.of(users));

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(usersDto);

        var expectRes = new HashMap<>();
        expectRes.put("fullName", "Full name must be at least 5 characters");

        // Perform request
        mockMvc.perform(post("/api/users/create")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    var actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, String>>() {});
                    assertEquals("create user failed",
                            expectRes.get("fullName"),
                            actual.get("fullName"));
                });
    }

    @Test
    @DisplayName("Fetch user successfully")
    public void testFetchUser() throws Exception {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();

        var expectRes = new HashMap<>();
        expectRes.put("id", 1);
        expectRes.put("username", "thainh");
        expectRes.put("fullName", "Nguyen Thai");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));

        // Perform request
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actualRes = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, Object>>() {});
                    assertEquals("fetch user id successful",
                            expectRes.get("id"),
                            actualRes.get("id"));
                    assertEquals("fetch user username successful",
                            expectRes.get("username"),
                            actualRes.get("username"));
                    assertEquals("fetch user full name successful",
                            expectRes.get("fullName"),
                            actualRes.get("fullName"));
                });
    }

    @Test
    @DisplayName("Fetch user failed: user not exist")
    public void testFetchUser1() throws Exception {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();

        var expectRes = new HashMap<>();
        expectRes.put("errorCode", "NOT_FOUND");

        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        // Perform request
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    var actualRes = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, Object>>() {});
                    assertEquals("fetch user id failed",
                            expectRes.get("errorCode"),
                            actualRes.get("errorCode"));
                });
    }

    @Test
    @DisplayName("Fetch user failed: internal server error")
    public void testFetchUser2() throws Exception {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();

        var expectRes = new HashMap<>();
        expectRes.put("errorCode", "INTERNAL_SERVER_ERROR");

        when(usersRepository.findById(1L)).thenThrow(new RuntimeException("failed"));

        // Perform request
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> {
                    var actualRes = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, Object>>() {});
                    assertEquals("fetch user id failed",
                            expectRes.get("errorCode"),
                            actualRes.get("errorCode"));
                });
    }

    @Test
    @DisplayName("Update user successfully")
    public void testUpdateUser() throws Exception {
        UsersDto usersDto = new UsersDto();
        usersDto.setFullName("Nguyen Thai update");

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(usersDto);

        var expectRes = new HashMap<>();
        expectRes.put("statusCode", "200");
        expectRes.put("statusMessage", "Updated successfully");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));

        // Perform request
        mockMvc.perform(patch("/api/users/update/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actualRes = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {});
                    assertEquals("update user successful",
                            expectRes,
                            actualRes);
                });
    }

    @Test
    @DisplayName("Update user failed: user not found")
    public void testUpdateUser1() throws Exception {
        UsersDto usersDto = new UsersDto();
        usersDto.setFullName("Nguyen Thai update");

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(usersDto);

        var expectRes = new HashMap<>();
        expectRes.put("errorCode", "NOT_FOUND");

        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        // Perform request
        mockMvc.perform(patch("/api/users/update/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    var actualRes = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, String>>() {});
                    assertEquals("update user successful",
                            expectRes.get("errorCode"),
                            actualRes.get("errorCode"));
                });
    }

    @Test
    @DisplayName("Update user failed: fullname is the same")
    public void testUpdateUser2() throws Exception {
        UsersDto usersDto = new UsersDto();
        usersDto.setFullName("Nguyen Thai");

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        //convert object to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(usersDto);

        var expectRes = new HashMap<>();
        expectRes.put("errorCode", "BAD_REQUEST");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));

        // Perform request
        mockMvc.perform(patch("/api/users/update/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    var actualRes = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, String>>() {});
                    assertEquals("update user failed",
                            expectRes.get("errorCode"),
                            actualRes.get("errorCode"));
                });
    }

    @Test
    @DisplayName("Delete user successfully")
    public void testDeleteUser() throws Exception {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        ObjectMapper objectMapper = new ObjectMapper();

        var expectRes = new HashMap<>();
        expectRes.put("statusCode", "200");
        expectRes.put("statusMessage", "Deleted successfully");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));

        // Perform request
        mockMvc.perform(delete("/api/users/delete/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var actualRes = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {});
                    assertEquals("delete user successful",
                            expectRes,
                            actualRes);
                });
    }

    @Test
    @DisplayName("Delete user failed: user not exist")
    public void testDeleteUser1() throws Exception {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("Nguyen Thai");

        ObjectMapper objectMapper = new ObjectMapper();

        var expectRes = new HashMap<>();
        expectRes.put("errorCode", "NOT_FOUND");

        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        // Perform request
        mockMvc.perform(delete("/api/users/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    var actualRes = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<Map<String, String>>() {});
                    assertEquals("delete user failed",
                            expectRes.get("errorCode"),
                            actualRes.get("errorCode"));
                });
    }
}
