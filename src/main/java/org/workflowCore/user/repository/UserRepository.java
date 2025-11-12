package org.workflowCore.user.repository;

// PURPOSE: User database operations

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.workflowCore.user.entity.User;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByUsername(String username){
        return find("username", username).firstResultOptional();
    }

    public Optional<User> findByEmail(String email){
        return find("email", email).firstResultOptional();
    }

    public boolean existByUsername(String username){
        return count("username", username) > 0 ;
    }

    public boolean existByEmail(String email){
        return count("email", email) > 0 ;
    }
}
