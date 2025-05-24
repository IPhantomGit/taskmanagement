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
}
