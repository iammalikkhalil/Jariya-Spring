package com.example.demo.infrastructure.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { } // ✅ Enable CORS
            .csrf { it.disable() } // Disable CSRF for APIs
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/api/**"
                    ).permitAll() // ✅ public APIs
                    .anyRequest().permitAll()
            }
            .httpBasic(Customizer.withDefaults())
            .formLogin { it.disable() }

        return http.build()
    }

    // ✅ Global CORS configuration — allows mobile apps / web clients to access freely
    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("*") // Allow all origins (mobile, web, etc.)
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        config.allowedHeaders = listOf("*")
        config.allowCredentials = false

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}