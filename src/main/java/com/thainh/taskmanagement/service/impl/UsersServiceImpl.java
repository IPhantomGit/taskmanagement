package com.thainh.taskmanagement.service.impl;

import com.thainh.taskmanagement.dto.UsersDto;
import com.thainh.taskmanagement.entity.Users;
import com.thainh.taskmanagement.mapper.UsersMapper;
import com.thainh.taskmanagement.repository.UsersRepository;
import com.thainh.taskmanagement.service.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {
    private UsersRepository usersRepository;

    @Override
    public List<UsersDto> fetchAllUsers() {
        List<Users> users = usersRepository.findAll(); // List<Users>
        return users.stream().map(user -> UsersMapper.mapToUsersDto(user, new UsersDto())).toList();
    }
}
