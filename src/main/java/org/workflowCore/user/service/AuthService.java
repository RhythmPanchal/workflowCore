package org.workflowCore.user.service;

// PURPOSE: Authentication business logic

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.workflowCore.Utils.DebugUtils;
import org.workflowCore.security.JwtService;
import org.workflowCore.user.dto.AuthResponse;
import org.workflowCore.user.dto.LoginDTO;
import org.workflowCore.user.dto.RegisterDTO;
import org.workflowCore.user.dto.UserDTO;
import org.workflowCore.user.entity.User;
import org.workflowCore.user.entity.UserRole;
import org.workflowCore.user.repository.UserRepository;

import java.util.Set;

@ApplicationScoped
public class AuthService {
    @Inject
    UserRepository userRepository;

    @Inject
    JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterDTO registerDTO){
        DebugUtils.printAsJson(registerDTO);
        if( userRepository.existByUsername(registerDTO.username)){
            throw new IllegalArgumentException("Username already exists");
        }

        if( userRepository.existByEmail(registerDTO.email)){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();

        user.username = registerDTO.username;
        user.email = registerDTO.email;
        user.password = BcryptUtil.bcryptHash(registerDTO.password) ;
        user.role = UserRole.USER;
        DebugUtils.printAsJson(user);
        userRepository.persist(user);

        System.out.print("Reached here");
        String token = jwtService.generateToken(user.username, Set.of(user.role.name()));

        return new AuthResponse(token, toDTO(user));
    }

    public AuthResponse login(LoginDTO loginDTO){
        User user = userRepository.findByUsername(loginDTO.username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password") );

        if( !BcryptUtil.matches(loginDTO.password, user.password)){
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.username, Set.of(user.role.name()));

        return new AuthResponse(token, toDTO(user));
    }

    public UserDTO getCurrentUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toDTO(user);
    }

    private UserDTO toDTO(User user){
        return new UserDTO(
                user.id,
                user.username,
                user.email,
                user.role,
                user.createdAt,
                user.updatedAt
        );
    }


}
