package com.thainh.taskmanagement.service;

import com.thainh.taskmanagement.dto.UsersDto;

import java.util.List;

public interface IUsersService {
    List<UsersDto> fetchAllUsers();
}
