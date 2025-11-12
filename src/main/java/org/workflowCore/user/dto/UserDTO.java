package org.workflowCore.user.dto;

import org.workflowCore.user.entity.UserRole;
import org.workflowCore.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {
    public Long id;
    public String username;
    public String email;
    public UserRole role;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public UserDTO(Long id, String username
    , String email, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserDTO(){}
}
