package com.project.antifakebook.rest;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/account")
public class AccountController {
    @GetMapping("/test")
        public String usingResponseEntityBuilderAndHttpHeaders() {
            return "test";
        }
}
