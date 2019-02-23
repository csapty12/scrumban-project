package com.scrumban.security;

import com.scrumban.model.domain.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.expirationTime}")
    private int expirationTime;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expirationDate = new Date(now.getTime()+expirationTime);
        String userId = Long.toString(user.getId());

        Map<String, Object>  claims = new HashMap<>();
        claims.put( "id", (Long.toString(user.getId())));
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(HS512, secret)
                .compact();

    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            log.warn("Invalid JWT signature");
        }
        catch(MalformedJwtException e){
            log.warn("Invalid JWT token ");
        }
        catch(ExpiredJwtException e){
            log.warn("JWT has expired");
        }
        catch(UnsupportedJwtException e){
            log.warn("JWT is not supported");
        }
        catch(IllegalArgumentException e){
            log.warn("JWT claim string is empty");
        }
        return  false;
    }

    public Long getUserIdFromToken(String token){
        Claims claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.parseLong((String) claims.get("id"));
    }
}
