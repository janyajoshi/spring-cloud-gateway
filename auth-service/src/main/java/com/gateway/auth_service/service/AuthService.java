package com.gateway.auth_service.service;

import com.gateway.auth_service.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    public String generateToken(String username, UserInfo userInfo) {
        return jwtService.generateToken(username, userInfo);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
