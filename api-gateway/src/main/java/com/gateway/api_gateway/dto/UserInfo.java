package com.gateway.api_gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private int id;
    private String name;
    private String email;
    private String password;
    private String roles;
}
