package com.smarttest.quizservice.configuration;

import com.smarttest.quizservice.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final String REGISTRATION_URL = "/api/v1/user/registration";
    private final String LOGIN_URL = "/api/v1/user/login";
    private final String LOGOUT_URL = "/api/v1/user/logout";
    private final String LOGIN_SUCCESS_URL = "/api/v1/user/info";

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(c -> c
                        .requestMatchers(REGISTRATION_URL).permitAll()
                        .requestMatchers(LOGIN_URL).permitAll()
                        .anyRequest().permitAll()
                ).formLogin(login -> login
                        .loginProcessingUrl(LOGIN_URL)
                        .defaultSuccessUrl(LOGIN_SUCCESS_URL))
                .logout(logout -> logout.logoutUrl(LOGOUT_URL));
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
