package com.thainh.taskmanagement.mapper;

import com.thainh.taskmanagement.dto.BugDto;
import com.thainh.taskmanagement.dto.TaskDto;
import com.thainh.taskmanagement.entity.Bug;
import com.thainh.taskmanagement.entity.Task;

public class TaskMapper {

    public static BugDto mapToBugDto(Bug bug, Task task, BugDto bugDto) {
        bugDto.setId(task.getId());
        bugDto.setSeverity(bug.getSeverity());
        bugDto.setStepsToReproduce(bug.getStepsToReproduce());
        bugDto.setExpectedResult(bug.getExpectedResult());
        bugDto.setActualResult(bug.getActualResult());
        bugDto.setTitle(task.getTitle());
        bugDto.setDescription(task.getDescription());
        bugDto.setUserId(task.getUserId());
        bugDto.setCategory(task.getCategory());
        bugDto.setCategoryId(task.getCategoryId());
        bugDto.setStatus(task.getStatus());
        return bugDto;
    }

    public static Task mapToTask(BugDto bugDto, Task task) {
        task.setDescription(bugDto.getDescription());
        task.setTitle(bugDto.getTitle());
        task.setUserId(bugDto.getUserId());
        task.setCategory(bugDto.getCategory());
        task.setCategoryId(bugDto.getCategoryId());
        task.setStatus(bugDto.getStatus());
        return task;
    }

    public static Bug mapToBug(BugDto bugDto, Bug bug) {
        bug.setSeverity(bugDto.getSeverity());
        bug.setStepsToReproduce(bugDto.getStepsToReproduce());
        bug.setExpectedResult(bugDto.getExpectedResult());
        bug.setActualResult(bugDto.getActualResult());
        return bug;
    }
}
