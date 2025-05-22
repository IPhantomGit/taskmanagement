package com.thainh.taskmanagement.service;

import com.thainh.taskmanagement.dto.UsersDto;

import java.util.List;

public interface IUsersService {
    List<UsersDto> fetchAllUsers();
    void createUser(UsersDto usersDto);
    void updateUser(Long id, UsersDto usersDto);
    void deleteUser(Long id);
    UsersDto fetchUserById(Long id);
}
