package com.thainh.taskmanagement.repository;

import com.thainh.taskmanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
