package com.smitsatwara.cinebook.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get Authorization header
        String header = request.getHeader("Authorization");

        // 2. Check if it starts with "Bearer "
        if (header != null && header.startsWith("Bearer ")) {

            // 3. Extract token
            String token = header.substring(7);

            // 4. Get email from token
            String extractedEmail = jwtUtil.getEmailFromToken(token);

            // 5. Check email exists and user not already authenticated
            if (extractedEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 6. Load user from database
                UserDetails userDetails = userDetailsService.loadUserByUsername(extractedEmail);

                // 7. Validate token
                if (jwtUtil.validateToken(token, extractedEmail)) {

                    // 8. Set authentication in SecurityContext
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // 9. Continue filter chain
        filterChain.doFilter(request, response);
    }
}