package com.lamayamei.pruebaviamaticabackend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long expirationTime;

    // Genera el token
  /*  public String generateToken(Authentication authentication) {
        String userName = authentication.getName();
        Date expiredDate = new Date(new Date().getTime() + expirationTime);
        return Jwts.builder().setSubject(userName).setIssuedAt(new Date()).setExpiration(expiredDate).signWith(key()).compact();
    }*/
    // Genera el token
    public String generateToken(Authentication authentication) {
        String userName = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date expiredDate = new Date(new Date().getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(userName)
                .claim("roles", roles)
                .claim("username", userName)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(key()).compact();
    }


    // Valida el token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key()).build().parse(token);
            return true;

        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new AuthenticationException(" JWT token invalido: " + e.getMessage()) {};
        }
    }

    // Obtiene el nombre de usuario del token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
