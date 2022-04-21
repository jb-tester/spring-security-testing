package com.mytests.spring.security.testing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * *
 * <p>Created by irina on 4/21/2022.</p>
 * <p>Project: spring-security-testing</p>
 * *
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String users(){
        return "users page";
    }

    @GetMapping("/{user}")
    public String usersByName(@PathVariable("user") String user) {
        return "for user " + user;
    }
}
