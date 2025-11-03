package org.workflowCore.task.dto;

// PURPOSE: Response object for API (doesn't expose internal details)

import org.workflowCore.task.entity.TaskPriority;
import org.workflowCore.task.entity.TaskStatus;

import java.time.LocalDateTime;

public class TaskDTO {
    public Long id;
    public String title;
    public String description;
    public TaskStatus status;
    public TaskPriority priority;
    public LocalDateTime createAt;
    public LocalDateTime updateAt;

    //constructor
    public TaskDTO(Long id, String title, String description, TaskStatus status, TaskPriority priority, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    //Empty constructor for JSON serialization
    public TaskDTO(){}
}
