package com.example.demo.config;

import lombok.experimental.NonFinal;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Danh sách các endpoint công khai, không yêu cầu xác thực
    private final String [] PUBLIC_ENPOINTS = {"/api/user", "/api/auth/token", "/api/auth/introspect"};

    @NonFinal
    protected static final String SIGN_KEY = ENVConfig.getEnv("JWT_SECRET");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry

                        // Cho phép tất cả mọi người truy cập các endpoint được liệt kê trong PUBLIC_ENPOINTS
                        .requestMatchers(HttpMethod.POST,PUBLIC_ENPOINTS).permitAll()
                        // Mọi request khác phải được xác thực
                        .anyRequest().authenticated());

        // Cấu hình để sử dụng OAuth2 Resource Server với JWT (JSON Web Token)
        httpSecurity.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                httpSecurityOAuth2ResourceServerConfigurer
                        .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));

        // Vô hiệu hóa CSRF (Cross-Site Request Forgery) để tránh yêu cầu xác thực CSRF token
        httpSecurity.csrf(HttpSecurityCSRFConfigure -> HttpSecurityCSRFConfigure.disable());
        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {

        // Tạo khóa bí mật (SecretKeySpec) từ chuỗi SIGN_KEY và chỉ định thuật toán HS512
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGN_KEY.getBytes(), "HS512");
        
        // Trả về một đối tượng JwtDecoder sử dụng NimbusJwtDecoder với khóa bí mật và thuật toán HMAC-SHA512
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

}
