package org.workflowCore.task.exception;
// PURPOSE: Custom exception for task not found
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
    public TaskNotFoundException(Long taskId) {
        super("Task with ID " + taskId + " not found.");
    }
}
