package com.gateway.auth_service.config;

import org.springframework.stereotype.Component;

@Component("Roles")
public final class Roles {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
}
