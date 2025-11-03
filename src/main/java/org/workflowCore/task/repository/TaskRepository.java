package org.workflowCore.task.repository;

//PURPOSE: Database access layer for tasks

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.workflowCore.task.entity.*;

import java.util.List;


import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.workflowCore.task.entity.Task;
import org.workflowCore.task.entity.TaskStatus;

import java.util.List;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {

    //find tasks by status with pagination
    public List<Task> findByStatus(TaskStatus status, int page, int size) {
        return find("status", status)
                .page(Page.of(page,size))
                .list();
    }

    public List<Task> findByPriority(TaskPriority priority, int page, int size){
        return find("priority", priority)
                .page(Page.of(page,size))
                .list();
    }

    public List<Task> searchByTitle(String title, int page, int size){
        return find("LOWER(title) LIKE LOWER(CONCAT('%', ?1, '%'))", title)
                .page(Page.of(page,size))
                .list();
    }

    public long countByStatus(TaskStatus status){
        return count("status", status);
    }

    //all task with pagination
    public List<Task> findAllTasks(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .list();
    }
}


