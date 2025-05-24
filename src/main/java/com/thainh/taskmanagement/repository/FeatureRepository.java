package com.thainh.taskmanagement.repository;

import com.thainh.taskmanagement.entity.Feature;
import com.thainh.taskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
}
