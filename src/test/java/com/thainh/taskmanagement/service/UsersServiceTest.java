package com.thainh.taskmanagement.service;

import com.thainh.taskmanagement.dto.UsersDto;
import com.thainh.taskmanagement.entity.Users;
import com.thainh.taskmanagement.repository.UsersRepository;
import com.thainh.taskmanagement.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepository;

    @Test
    @DisplayName("testGetAllUsers successfully")
    public void testGetAllUsers() {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        when(usersRepository.findAllByOrderByIdAscCreatedAtAsc()).thenReturn(List.of(users));
        assertEquals(1, usersService.fetchAllUsers().size());
    }

    @Test
    @DisplayName("test update user successfully")
    public void testUpdateUser() {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("abc123");

        UsersDto usersDto = new UsersDto();
        usersDto.setId(1L);
        usersDto.setFullName("Nguyen Thai");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        usersService.updateUser(1L, usersDto);
        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        verify(usersRepository).save(captor.capture());
        assertEquals("Nguyen Thai", captor.getValue().getFullName());
    }
}
