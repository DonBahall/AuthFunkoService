package com.example.authfunkoservice.Resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WakeUpResolver {
    @QueryMapping
    public String wakeup(){
        return "wakeup";
    }
}
