package com.example.authfunkoservice.Resolver;


import com.example.authfunkoservice.Request.AuthenticationRequest;
import com.example.authfunkoservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthenticationQueryResolver {

    private final UserService service;

    @QueryMapping
    public String authenticate(@Argument String email,@Argument String password) {
        return service.authenticate(new AuthenticationRequest(email, password));
    }

}
