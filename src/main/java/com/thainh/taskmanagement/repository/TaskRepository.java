package com.thainh.taskmanagement.repository;

import com.thainh.taskmanagement.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = """
            SELECT * FROM task t
            WHERE (:status IS NULL OR t.status = :status)
              AND (:userId IS NULL OR t.user_id = :userId)
              AND (:title IS NULL OR t.title ILIKE CONCAT('%', :title, '%'))
              AND (:description IS NULL OR t.description ILIKE CONCAT('%', :description, '%'))
            ORDER BY t.created_at
            """,
            countQuery = """
                    SELECT COUNT(*) FROM task t
                    WHERE (:status IS NULL OR t.status = :status)
                      AND (:userId IS NULL OR t.user_id = :userId)
                      AND (:title IS NULL OR t.title ILIKE CONCAT('%', :title, '%'))
                      AND (:description IS NULL OR t.description ILIKE CONCAT('%', :description, '%'))
                    """,
            nativeQuery = true)
    Page<Task> searchTasks(
            @Param("status") Integer status,
            @Param("userId") Long userId,
            @Param("title") String title,
            @Param("description") String description,
            Pageable pageable
    );

}
