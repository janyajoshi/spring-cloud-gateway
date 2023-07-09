package com.gateway.api_gateway.filter;

import com.gateway.api_gateway.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    public static class Config { }

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest modRquest = null;

//            check if applicable with our RouteValidator
            if (validator.isSecured.test(exchange.getRequest())) {

//                check if header is included
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Authorization Header Missing");
                }

//                retrieve bearer token
                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

//                can call auth service and validate.
//                to reduce network calls, we will validate here itself
//                also, we may need to mask token for transfer or else it can be intercepted.

                Boolean isAuthenticated = jwtUtil.validateToken(token);
                if (isAuthenticated.equals(Boolean.FALSE)){
                    System.out.println("Invalid Token. Throwing Runtime Exception");
                    throw new RuntimeException("un authorized access to application");
                }

//                extract user details from token payload and add that to request header
//                to use in downstream applications.
//                TODO: find some way to pass entire userDetails to downsteam services.
                modRquest = exchange.getRequest()
                        .mutate()
                        .header("loggedInUser", jwtUtil.extractUsername(token))
                        .build();
            }
            return chain.filter(exchange.mutate().request(modRquest).build());
        });
    }
}
