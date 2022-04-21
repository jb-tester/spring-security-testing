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
@RequestMapping("/adm")
public class AdminController {

    @GetMapping
    public String top(){
        return "for admins";
    }

    @GetMapping("/{pvar}")
    public String adminsByVar(@PathVariable("pvar") String pvar) {
        return "for admins got " + pvar;
    }
}
