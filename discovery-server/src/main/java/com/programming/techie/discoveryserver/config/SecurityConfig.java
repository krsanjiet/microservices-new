package com.programming.techie.discoveryserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Value("${eureka.username}")
    private String username;

    @Value("${eureka.password}")
    private String password;

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(authorize ->authorize.anyRequest().authenticated())
                .csrf()
                .disable()
                .httpBasic();

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsManager(){
        UserDetails userDetails = User.builder()
                .username(username)
                .password("{noop}"+password)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}
