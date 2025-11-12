package org.workflowCore.security;

// PURPOSE: JWT token generation and validation

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    @Inject
    JWTParser jwtParser;

    public String generateToken(String username, Set<String> roles ){
        return Jwt.issuer("https://workdflowCore.com/issuer")
                .upn(username) //User Principle Name : sets name for that token
                .groups(roles) //give group according to roles, used for role based authorization
                .expiresIn(Duration.ofHours(24))
                .sign();
    }

    public JsonWebToken parseToken(String token) throws ParseException {
        System.out.println("Parsing token: " + token);
        return jwtParser.parse(token);
    }

    public String getUsernameFromToken(String token){
        try{
            JsonWebToken jwt = parseToken(token);
            System.out.println("Parsed JWT for user: " + jwt.getName());
            return jwt.getName();
        } catch(ParseException e ){
            return null;
        }
    }

}
