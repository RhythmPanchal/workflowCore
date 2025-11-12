package org.workflowCore.user.entity;

// PURPOSE: User entity with authentication details

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import io.quarkus.security.jpa.Password;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.jose4j.jwk.Use;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@UserDefinition
public class User extends PanacheEntity {

    @Username
    @NotBlank(message = "Username is required")
    @Size(min=3 , max = 50)
    @Column(unique = true, nullable = false)
    public String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    @Column(unique = true, nullable = false)
    public String email;

    @Password
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    public String password;

    //@Roles
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public UserRole role = UserRole.USER;

    //this is for quarkus security as it expects string or collection of string only.
    //this will convert your enum into a string.
    @Roles
    public String getRoleForSecurity(){
        return this.role.name();
    }

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static User findByUsername(String username) {
        return find("username", username).firstResult();
    }
    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
