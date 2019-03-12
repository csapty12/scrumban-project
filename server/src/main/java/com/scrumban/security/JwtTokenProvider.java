package com.scrumban.security;

import com.scrumban.model.domain.User;
import com.scrumban.security.payload.LoginRequest;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

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
        System.out.println("claims: " + claims.toString());
        return Long.parseLong((String) claims.get("id"));
    }

    public String getJwtForAuthenticatedUser(@Valid @RequestBody LoginRequest loginRequest,
                                             AuthenticationManager authenticationManager) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String generatedToken = tokenPrefix + generateToken(authentication);
        if(!generatedToken.isEmpty()){
            log.info("login has been attempted, and token has been generated");
        }
        else{
            log.info("There has been a problem creating a token.");
        }
        return generatedToken;
    }
}
