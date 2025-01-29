package com.ericson.tiendasmartech.security;

import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFilterSecurity extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                String username = jwtService.getUsernameToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtService.expiredToken(token)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        username,
                                        null,
                                        null
                                );
                        authenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage("Expired Jwt exception");
            response.getWriter().write(new ObjectMapper().writeValueAsString(controllerResponse));
            return;
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage("Jwt Exception");
            response.getWriter().write(new ObjectMapper().writeValueAsString(controllerResponse));
            return;
        } catch (AuthenticationServiceException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage("Authentication service exception");
            response.getWriter().write(new ObjectMapper().writeValueAsString(controllerResponse));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage("Exception");
            response.getWriter().write(new ObjectMapper().writeValueAsString(controllerResponse));
        }

        filterChain.doFilter(request, response);
    }
}
