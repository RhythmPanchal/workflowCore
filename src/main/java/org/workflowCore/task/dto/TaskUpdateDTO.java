package org.workflowCore.task.dto;

// PURPOSE: Request object for updating tasks

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.workflowCore.task.entity.TaskPriority;
import org.workflowCore.task.entity.TaskStatus;

public class TaskUpdateDTO {
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    public String title;

    @Size(max = 1000, message = "Description can be up to 1000 characters")
    public String description;


    public TaskStatus status;

    public TaskPriority priority;
}
