package com.thainh.taskmanagement.service;

import com.thainh.taskmanagement.dto.UsersDto;
import com.thainh.taskmanagement.entity.Users;
import com.thainh.taskmanagement.exception.ResourceNotFoundException;
import com.thainh.taskmanagement.exception.UserExistException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepository;

    @Test
    @DisplayName("testGetAllUsers successfully: not empty list")
    public void testGetAllUsers() {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        when(usersRepository.findAllByOrderByIdAscCreatedAtAsc()).thenReturn(List.of(users));
        assertEquals(1, usersService.fetchAllUsers().size());
    }

    @Test
    @DisplayName("testGetAllUsers successfully: empty list")
    public void testGetAllUsers1() {
        when(usersRepository.findAllByOrderByIdAscCreatedAtAsc()).thenReturn(List.of());
        assertEquals(0, usersService.fetchAllUsers().size());
    }

    @Test
    @DisplayName("testGetAllUsers failed: exception error")
    public void testGetAllUsers2() {
        when(usersRepository.findAllByOrderByIdAscCreatedAtAsc()).thenThrow(new RuntimeException("failed"));
        assertThrows(RuntimeException.class,() -> usersService.fetchAllUsers());
    }

    @Test
    @DisplayName("test create user successfully")
    public void testCreateUser1() {
        UsersDto usersDto = new UsersDto();
        usersDto.setUsername("thainh");
        usersDto.setFullName("abc123");

        usersService.createUser(usersDto);
        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        verify(usersRepository).save(captor.capture());
        assertEquals("abc123", captor.getValue().getFullName());
    }

    @Test
    @DisplayName("test create user failed: user exist")
    public void testCreateUser2() {
        UsersDto usersDto = new UsersDto();
        usersDto.setUsername("thainh");
        usersDto.setFullName("abc123");

        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("abc123");

        when(usersRepository.findByUsername("thainh")).thenReturn(Optional.of(users));

        assertThrows(UserExistException.class, () -> usersService.createUser(usersDto));
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

    @Test
    @DisplayName("test update user failed: user not exist")
    public void testUpdateUser1() {
        UsersDto usersDto = new UsersDto();
        usersDto.setId(1L);
        usersDto.setFullName("Nguyen Thai");

        when(usersRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> usersService.updateUser(1L, usersDto));
    }

    @Test
    @DisplayName("test update user failed: fullname is the same")
    public void testUpdateUser2() {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("abc123");

        UsersDto usersDto = new UsersDto();
        usersDto.setId(1L);
        usersDto.setFullName("abc123");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        assertThrows(UserExistException.class, () -> usersService.updateUser(1L, usersDto));
    }

    @Test
    @DisplayName("test delete user successfully")
    public void testDeleteUser1() {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("abc123");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        usersService.deleteUser(1L);
        verify(usersRepository).delete(captor.capture());
        assertEquals("thainh", captor.getValue().getUsername());
    }

    @Test
    @DisplayName("test delete user failed: user not exist")
    public void testDeleteUser2() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> usersService.deleteUser(1L));
    }

    @Test
    @DisplayName("test get user successfully")
    public void testGetUser() {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("thainh");
        users.setFullName("abc123");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        UsersDto usersDto = usersService.fetchUserById(1L);
        assertEquals("abc123", usersDto.getFullName());
    }

    @Test
    @DisplayName("test get user failed: user not exist")
    public void testGetUser1() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> usersService.fetchUserById(1L));
    }
}
