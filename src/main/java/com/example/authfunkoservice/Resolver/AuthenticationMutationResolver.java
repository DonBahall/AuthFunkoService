package com.example.authfunkoservice.Resolver;

import com.example.authfunkoservice.Model.User.User;
import com.example.authfunkoservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthenticationMutationResolver {
    private final UserService service;

    @MutationMapping
    public User registration(@Argument User user){
        return service.registerUser(user);
    }
    @MutationMapping
    public User updateUser(@Argument User user){
        return service.updateUser(user);
    }

}
