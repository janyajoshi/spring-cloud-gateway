package com.gateway.auth_service.config;

import com.gateway.auth_service.dto.UserInfo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserInfoUserDetails implements UserDetails {
    private UserInfo userInfo;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.authorities = Arrays
                .stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
