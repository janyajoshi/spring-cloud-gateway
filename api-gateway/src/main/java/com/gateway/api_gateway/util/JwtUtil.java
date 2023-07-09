package com.gateway.api_gateway.util;

import com.gateway.api_gateway.dto.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.function.Function;

@Component
public class JwtUtil {
    public static final String SECRET = "Secret123Secret123Secret123Secret123Secret123";

    public boolean validateToken(final String token) {
        boolean status = true;
        try{
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
        } catch (SignatureException e){
            status = false;
        }
        return status;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public UserInfo extractUserInfo(String token){
        final Claims claims = extractAllClaims(token);
        UserInfo userInfo = claims.get("abc", UserInfo.class);
        return userInfo;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
