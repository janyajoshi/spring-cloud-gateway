package com.gateway.auth_service.service;

import com.gateway.auth_service.config.UserInfoUserDetails;
import com.gateway.auth_service.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    SampleUserRepository sampleUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = sampleUserRepository.findUserByName(username);
        return userInfo.map(UserInfoUserDetails::new).orElseThrow(()->new UsernameNotFoundException("user " + username + " not found"));
    }
}
