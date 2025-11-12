package org.workflowCore.user.resource;

// PURPOSE: Authentication REST endpoints

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.workflowCore.user.dto.AuthResponse;
import org.workflowCore.user.dto.LoginDTO;
import org.workflowCore.user.dto.RegisterDTO;
import org.workflowCore.user.dto.UserDTO;
import org.workflowCore.user.service.AuthService;

import java.awt.image.RescaleOp;

@Path("api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @PermitAll
    public Response register(@Valid RegisterDTO registerDTO){
        AuthResponse response = authService.register(registerDTO);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/login")
    @PermitAll
    public Response login(@Valid LoginDTO loginDTO){
        AuthResponse response = authService.login(loginDTO);
        return Response.ok(response).build();
    }

    @GET
    @Path("/me")
    @RolesAllowed({"USER", "ADMIN"})
    public Response getCurrentUser(@Context SecurityContext securityContext){
        String username = securityContext.getUserPrincipal().getName();
        UserDTO user = authService.getCurrentUser(username);
        return Response.ok(user).build();
    }
}
