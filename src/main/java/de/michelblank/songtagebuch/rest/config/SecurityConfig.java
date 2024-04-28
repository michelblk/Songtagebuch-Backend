package de.michelblank.songtagebuch.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .oauth2Login(Customizer.withDefaults())
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.
                                sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers("/oauth2/authorization/spotify").permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user").authenticated()
                                .requestMatchers(HttpMethod.GET, "/users/{userid}/diary").authenticated() // TODO validate user is only accessing own data
                                .requestMatchers(HttpMethod.PUT, "/users/{userid}/diary").authenticated() // TODO validate user is only putting own data
                                .requestMatchers(HttpMethod.POST, "/users/{userid}/diary/{diaryEntryId}").authenticated() // TODO validate user is only putting own data
                                .requestMatchers(HttpMethod.GET, "/song/search").authenticated()
                                .anyRequest().denyAll()
                );

        return http.build();
    }
}
