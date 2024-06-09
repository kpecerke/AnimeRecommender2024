package com.example.demo;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Získanie hlavičky Authorization z požiadavky
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Kontrola, či je hlavička prítomná a začína s "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extrahovanie JWT tokenu z hlavičky
            jwt = authorizationHeader.substring(7);
            try {
                // Extrahovanie používateľského mena z JWT tokenu
                username = jwtUtil.extractUsername(jwt);
            } catch (ExpiredJwtException e) {
                // Spracovanie prípadu, keď je JWT token expirovaný
                logger.warn("JWT token is expired");
            }
        }

        // Ak je používateľské meno získané a kontext zabezpečenia nie je nastavený
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Načítanie detailov používateľa pomocou UserDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // Overenie platnosti tokenu
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // Vytvorenie autentizačného tokenu
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Nastavenie detailov autentizácie
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Nastavenie autentizácie v kontexte zabezpečenia
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // Pokračovanie vo filtračnom reťazci
        chain.doFilter(request, response);
    }
}
