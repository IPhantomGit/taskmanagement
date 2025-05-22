package com.thainh.taskmanagement.repository;

import com.thainh.taskmanagement.entity.Bug;
import com.thainh.taskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Long> {
}
