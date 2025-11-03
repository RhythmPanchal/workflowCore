package org.workflowCore.task.entity;

// PURPOSE: Represents a task in the database

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends PanacheEntity {
// id is automatically created in panache

    @NotBlank(message = "Title is mandatory")
    @Size(min = 3,  max = 200 , message = "Title must be between 3 and 200 characters")
    @Column(nullable = false)
    public String title;

    @Size(max = 1000 , message = "Description can be up to 1000 characters")
    @Column(length = 1000)
    public String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TaskStatus status = TaskStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TaskPriority priority = TaskPriority.MEDIUM;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createAt ;

    @Column(name = "updated_at")
    public LocalDateTime updateAt ;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }
}

