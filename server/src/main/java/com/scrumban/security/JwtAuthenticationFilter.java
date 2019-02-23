package com.scrumban.security;

import com.scrumban.model.domain.User;
import com.scrumban.service.user.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Value("${jwt.header.string}")
    private String headerString;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwt = getJwtFromRequest(httpServletRequest);
            if(hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
                Long userId =  jwtTokenProvider.getUserIdFromToken(jwt);
                User user = userDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.emptyList());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken );
            }

        }catch(Exception e){
            log.error("could not set user authentication in security context" + e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken=request.getHeader(headerString);

        if(hasText(bearerToken) && bearerToken.startsWith(tokenPrefix)){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;

    }
}
