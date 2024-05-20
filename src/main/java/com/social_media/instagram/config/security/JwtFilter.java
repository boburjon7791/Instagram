package com.social_media.instagram.config.security;

import com.social_media.instagram.model.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization==null || authorization.isBlank() || authorization.length()<20) {
            filterChain.doFilter(request, response);
            return;
        }
        User user = jwtProvider.extractToken(authorization);
        WebAuthenticationDetails details=new WebAuthenticationDetails(request);
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        token.setDetails(details);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    @Autowired
    public void setJwtProvider(@Lazy JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
}
