package org.workflowCore;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
@ApplicationScoped
public class MyEntity {
    @GET // This means the method responds to HTTP GET requests
    @Produces(MediaType.TEXT_PLAIN) // This tells Quarkus to return plain text
    public String hello() {
        return "Hello World!";
    }
}