package com.gateway.auth_service.controller;

import com.gateway.auth_service.config.UserInfoUserDetails;
import com.gateway.auth_service.dto.AuthRequest;
import com.gateway.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to unsecured endpoint";
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
//            add user info object to token
            return authService.generateToken(
                    authRequest.getUsername(),
                    ((UserInfoUserDetails) authenticate.getPrincipal()).getUserInfo()
            );
        } else {
            throw new RuntimeException("Cannot Authenticate User");
        }
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam("token") String token) {
        return authService.validateToken(token);
    }
}
