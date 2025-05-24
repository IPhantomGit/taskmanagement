package com.thainh.taskmanagement.repository;

import com.thainh.taskmanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findAllByOrderByIdAscCreatedAtAsc();
    Optional<Users> findByUsername(String username);
}
