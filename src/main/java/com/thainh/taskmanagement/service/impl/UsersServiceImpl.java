package com.thainh.taskmanagement.service.impl;

import com.thainh.taskmanagement.dto.UsersDto;
import com.thainh.taskmanagement.entity.Users;
import com.thainh.taskmanagement.exception.ResourceNotFoundException;
import com.thainh.taskmanagement.exception.UserExistException;
import com.thainh.taskmanagement.mapper.UsersMapper;
import com.thainh.taskmanagement.repository.UsersRepository;
import com.thainh.taskmanagement.service.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {
    private UsersRepository usersRepository;

    @Override
    public List<UsersDto> fetchAllUsers() {
        List<Users> users = usersRepository.findAll(); // List<Users>
        return users.stream().map(user -> UsersMapper.mapToUsersDto(user, new UsersDto())).toList();
    }

    @Override
    public void createUser(UsersDto usersDto) {
        Users users = UsersMapper.mapToUsers(usersDto, new Users()); // Users
        Optional<Users> existingUser = usersRepository.findByUsername(users.getUsername()); // Optional<Users>
        if (existingUser.isPresent()) {
            throw new UserExistException("Username [" + users.getUsername() + "] already exists");
        }
        usersRepository.save(users);
    }

    @Override
    public void updateUser(Long id, UsersDto usersDto) {
        Users existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", "" + id));
        if (usersDto.getFullName() == null ||
                usersDto.getFullName().isEmpty() ||
                usersDto.getFullName().equals(existingUser.getFullName())) {
            throw new UserExistException("Nothing to update");
        }
        existingUser.setFullName(usersDto.getFullName());
        existingUser.setFullName(existingUser.getFullName());
        usersRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        Users existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", "" + id));
        usersRepository.delete(existingUser);
    }

    @Override
    public UsersDto fetchUserById(Long id) {
        Users existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", "" + id));
        return UsersMapper.mapToUsersDto(existingUser, new UsersDto());
    }
}
