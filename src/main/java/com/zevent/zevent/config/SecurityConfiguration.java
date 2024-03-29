package com.zevent.zevent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
        private static final String[] PERMITTED_URLS = {
                        "auth/**",
        };

        @Autowired
        private JwtAuthFilter jwtAuthFilter;

        @Autowired
        private AuthenticationProvider authenticationProvider;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configurationSource(request -> {
                                        var corsConfiguration = new CorsConfiguration();
                                        corsConfiguration.applyPermitDefaultValues();
                                        corsConfiguration.addAllowedMethod("POST");
                                        corsConfiguration.addAllowedMethod("GET");
                                        return corsConfiguration;
                                }))
                                .authorizeHttpRequests(
                                                req -> req.requestMatchers(HttpMethod.GET, "events/**").permitAll()
                                                                .requestMatchers(PERMITTED_URLS)
                                                                .permitAll()
                                                                .anyRequest()
                                                                .authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

}
