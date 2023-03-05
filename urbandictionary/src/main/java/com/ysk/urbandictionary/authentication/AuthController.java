package com.ysk.urbandictionary.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/api/auth")
    AuthResponse handleAuthentication(@RequestBody Credentials credentials) {
        return authService.authenticate(credentials);
    }

}


