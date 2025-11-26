package org.art.tetragallery.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.art.tetragallery.model.entity.User;
import org.art.tetragallery.repository.UserRep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRep userRep;

    public SecurityConfig(JwtService jwtService, UserRep userRep) {
        this.jwtService = jwtService;
        this.userRep = userRep;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/customer/**").permitAll()
                        .requestMatchers("/api/artist/**").permitAll()
                        .requestMatchers("/api/reset/**").permitAll()
                        .requestMatchers("/api/art/**").permitAll()
                        .requestMatchers("/api/dev/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Admin-only
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Default allow
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtAuthFilter(jwtService, userRep),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // JWT filter
    static class JwtAuthFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final UserRep userRep;

        public JwtAuthFilter(JwtService jwtService, UserRep userRep) {
            this.jwtService = jwtService;
            this.userRep = userRep;
        }

        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain
        ) throws ServletException, IOException {

            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                String token = header.substring(7);
                DecodedJWT decoded = jwtService.verify(token);

                Long userId = Long.valueOf(decoded.getSubject());
                String role = decoded.getClaim("role").asString(); // "ADMIN", "ARTIST", etc.

                User user = userRep.findById(userId).orElse(null);
                if (user == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                String springRole = "ROLE_" + role;

                var auth = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority(springRole))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) {
                // Invalid token
            }

            filterChain.doFilter(request, response);
        }
    }
}
