package org.workflowCore.task.service;

// PURPOSE: Business logic for task operations

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.workflowCore.task.dto.TaskCreateDTO;
import org.workflowCore.task.dto.TaskDTO;
import org.workflowCore.task.dto.TaskUpdateDTO;
import org.workflowCore.task.entity.Task;
import org.workflowCore.task.entity.TaskPriority;
import org.workflowCore.task.entity.TaskStatus;
import org.workflowCore.task.exception.TaskNotFoundException;
import org.workflowCore.task.repository.TaskRepository;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository taskRepository;

    @Transactional
    public TaskDTO createTask(TaskCreateDTO createDTO) {
        Task task = new Task();
        task.title = createDTO.title;
        task.description = createDTO.description;
        task.status = createDTO.status != null ? createDTO.status : TaskStatus.PENDING;
        task.priority = createDTO.priority != null ? createDTO.priority : TaskPriority.MEDIUM;
        taskRepository.persist(task);
        return toDTO(task);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskUpdateDTO updateDTO){
       Task task = taskRepository.findByIdOptional(id)
               .orElseThrow(  ()-> new TaskNotFoundException(id));

       if( updateDTO.title != null ){
           task.title = updateDTO.title;
       }
       if( updateDTO.description != null ) {
           task.description = updateDTO.description;
       }
         if( updateDTO.status != null ){
              task.status = updateDTO.status;
         }
        if( updateDTO.priority != null ){
            task.priority = updateDTO.priority;
        }
            return toDTO(task);
    }


    @Transactional
    public void deleteTask(Long id){
        if( !taskRepository.deleteById(id)){
            throw new TaskNotFoundException(id);
        }
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findByIdOptional(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return toDTO(task);
    }

    public List<TaskDTO> getAllTasks(int page, int size) {
        return taskRepository.findAllTasks(page, size)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByStatus(TaskStatus status, int page, int size) {
        return taskRepository.findByStatus(status, page, size)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByPriority(TaskPriority priority, int page, int size) {
        return taskRepository.findByPriority(priority, page, size)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> searchTasks(String keyword, int page, int size) {
        return taskRepository.searchByTitle(keyword, page, size)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Map<TaskStatus, Long> getTaskStatistics() {
        Map<TaskStatus, Long> stats = new HashMap<>();
        for (TaskStatus status : TaskStatus.values()) {
            stats.put(status, taskRepository.countByStatus(status));
        }
        return stats;
    }
    private TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.id,
                task.title,
                task.description,
                task.status,
                task.priority,
                task.createAt,
                task.updateAt
        );
    }



}
