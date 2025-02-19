package com.kbtg.bootcamp.posttest.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers("/admin/**").authenticated()
                                .requestMatchers("/users", "/lotteries", "/users/*/lotteries", "/users/*/lotteries/*", "/*/lotteries/*").permitAll()
                )
                .httpBasic(withDefaults())
                .build();
    }



}