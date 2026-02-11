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
                                "/productservice/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> {
                            jwtSpec.jwtDecoder(reactiveJwtDecoder());
                        }));
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder.withSecretKey(new SecretKeySpec(
                Base64.getDecoder().decode(jwtSecret), "HmacSHA256")).build();
    }

}
