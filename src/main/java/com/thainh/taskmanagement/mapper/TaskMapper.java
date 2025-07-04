package com.thainh.taskmanagement.mapper;

import com.thainh.taskmanagement.dto.BugDto;
import com.thainh.taskmanagement.dto.FeatureDto;
import com.thainh.taskmanagement.entity.Bug;
import com.thainh.taskmanagement.entity.Feature;
import com.thainh.taskmanagement.entity.Task;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        bugDto.setCreatedAt(task.getCreatedAt().format(df));
        return bugDto;
    }

    public static Task mapToTask(BugDto bugDto, Task task) {
        task.setTitle(bugDto.getTitle());
        task.setDescription(bugDto.getDescription());
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

    public static FeatureDto mapToFeatureDto(Feature feature, Task task, FeatureDto featureDto) {
        featureDto.setId(task.getId());
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        featureDto.setDeadline(feature.getDeadline().format(sdf));
        featureDto.setTitle(task.getTitle());
        featureDto.setBusinessValue(feature.getBusinessValue());
        featureDto.setDescription(task.getDescription());
        featureDto.setUserId(task.getUserId());
        featureDto.setCategory(task.getCategory());
        featureDto.setCategoryId(task.getCategoryId());
        featureDto.setStatus(task.getStatus());
        featureDto.setCreatedAt(task.getCreatedAt().format(sdf));
        return featureDto;
    }

    public static Task mapToTask(FeatureDto featureDto, Task task) {
        task.setDescription(featureDto.getDescription());
        task.setTitle(featureDto.getTitle());
        task.setUserId(featureDto.getUserId());
        task.setCategory(featureDto.getCategory());
        task.setCategoryId(featureDto.getCategoryId());
        task.setStatus(featureDto.getStatus());
        return task;
    }

    public static Feature mapToFeature(FeatureDto featureDto, Feature feature) throws ParseException {
        feature.setBusinessValue(featureDto.getBusinessValue());
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        feature.setDeadline(LocalDate.parse(featureDto.getDeadline(), sdf));
        return feature;
    }
}
