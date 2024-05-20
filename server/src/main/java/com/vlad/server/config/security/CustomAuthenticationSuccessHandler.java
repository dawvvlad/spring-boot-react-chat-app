package com.vlad.server.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Long userId = ((MyUserDetail) authentication.getPrincipal()).getId();

        String targetUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/")
                .path(String.valueOf(userId))
                .toUriString();

        response.sendRedirect(targetUrl);
    }

}
