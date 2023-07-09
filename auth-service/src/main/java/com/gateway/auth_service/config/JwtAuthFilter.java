package com.gateway.auth_service.config;

import com.gateway.auth_service.service.JwtService;
import com.gateway.auth_service.service.UserInfoUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoUserDetailsService userInfoUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader!=null && authHeader.startsWith("Bearer ")){
//            get token
            token = authHeader.substring(7);
//            get username
            username = jwtService.extractUsername(token);
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
//            load user details
            UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(username);
//            check if token is valid
            if (jwtService.validateToken(token, userDetails)){
//                create authentication object and set it to Security Context Holder
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
