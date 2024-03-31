package com.licenta.licenta.utils;

import com.licenta.licenta.domain.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    private String secret = "penisaaaaaaaaa";

    private long JWT_TOKEN_VALIDITY = 1000L;

    private Map<String, List<String>> invalidTokens = new HashMap<>();

    public String getUsernameFromToken(final String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(final String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        try{
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }catch (ExpiredJwtException e){
            return true;
        }
    }

    public Date getExpirationDateFromToken(final String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(final UserPrincipal userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("password", userDetails.getPassword());
        return  doGenerateToken(claims,userDetails.getUsername());
    }

    public String doGenerateToken(final Map<String, Object> claims, final String username){
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public Boolean validateToken(String token, final UserDetails userDetails){
        final String username = userDetails.getUsername();
        return (username.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }

    public Boolean isTokenValid(final String token){
        return !isTokenExpired(token);
    }
    public boolean isUsed(final String token){
        if (this.invalidTokens.containsKey(this.getUsernameFromToken(token))){
            return this.invalidTokens.get(this.getUsernameFromToken(token)).contains(token);
        }
        return false;
    }

    public void invalidateToken(final String token){
        if (this.invalidTokens.containsKey(this.getUsernameFromToken(token))){
            this.invalidTokens.get(this.getUsernameFromToken(token)).add(token);
        }
        else {
            this.invalidTokens.putIfAbsent(this.getUsernameFromToken(token),List.of(token));
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

