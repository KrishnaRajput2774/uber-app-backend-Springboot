package com.rk.uberApp.security;

import com.rk.uberApp.entities.User;
import com.rk.uberApp.services.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwtTokenWithBearer = request.getHeader("Authorization");

            if( jwtTokenWithBearer == null || !jwtTokenWithBearer.startsWith("Bearer ")) {
                filterChain.doFilter(request,response);
                return;
            }
            String jwtToken = jwtTokenWithBearer.split("Bearer ")[1];
            Long id = jwtService.getUserIdFromToken(jwtToken);

            if( id != null && SecurityContextHolder.getContext().getAuthentication()==null) {
                User user = userService.findUserById(id);

                UsernamePasswordAuthenticationToken authenticationObj =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                authenticationObj.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationObj);

            }
            filterChain.doFilter(request,response);
        } catch (JwtException e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }
    }
}
