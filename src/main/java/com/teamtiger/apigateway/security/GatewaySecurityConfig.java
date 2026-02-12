package com.teamtiger.apigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class GatewaySecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * This enforces a valid token on every request that passes through, except for the urls specified
     * Disables CSRF
     * Specifies URL's on a Whitelist
     * Checks access token for valid signature and expiry
     * @param http ServerHttpSecurity allows configuration for http requests
     * @return The configured SecurityWebFilterChain
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/api/users/login",
                                "/api/users/register",
                                "/api/auth/refresh",
                                "/api/vendors/login",
                                "/api/vendors/register",
                                "/actuator/**",
                                "/api/userservice/**",
                                "/userservice/**",
                                "/api/productservice/**",
                                "/productservice/**",
                                "/api/forecastservice/**",
                                "/api/forecast/actuator").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> {
                            jwtSpec.jwtDecoder(reactiveJwtDecoder());
                        }));
        return http.build();
    }

    /**
     * Explicitly states a decoder for the JWT tokens
     * Algorithm: HmacSHA256
     * @return A ReactiveJwtDecoder that's used by the Security Web Filter
     */
    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder.withSecretKey(new SecretKeySpec(
                Base64.getDecoder().decode(jwtSecret), "HmacSHA256")).build();
    }

}
