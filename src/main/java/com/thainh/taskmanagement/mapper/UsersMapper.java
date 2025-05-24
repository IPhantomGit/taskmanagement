package com.thainh.taskmanagement.mapper;

import com.thainh.taskmanagement.dto.UsersDto;
import com.thainh.taskmanagement.entity.Users;

public class UsersMapper {

    public static UsersDto mapToUsersDto(Users users, UsersDto usersDto) {
        usersDto.setId(users.getId());
        usersDto.setUsername(users.getUsername());
        usersDto.setFullName(users.getFullName());
        usersDto.setPassword(users.getPassword());
        return usersDto;
    }

    public static Users mapToUsers(UsersDto usersDto, Users users) {
        users.setUsername(usersDto.getUsername());
        users.setFullName(usersDto.getFullName());
        users.setPassword(usersDto.getPassword());
        return users;
    }
}
