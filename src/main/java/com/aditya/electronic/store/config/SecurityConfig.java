package com.aditya.electronic.store.config;

import com.aditya.electronic.store.security.JwtAuthenticationEntryPoint;
import com.aditya.electronic.store.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        //cors disable
        security.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
        //csrf disable
        security.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());


        security.authorizeHttpRequests(request->{
            request
                    .requestMatchers(HttpMethod.DELETE,"/users/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/users/**").hasAnyRole("ADMIN","NORMAL")

                    .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
                    .requestMatchers("/products/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/users/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/users").permitAll()
                    .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
                    .requestMatchers("/categories/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST,"/auth/generate-token").permitAll()
                    .requestMatchers("/auth/**").authenticated()
                    .anyRequest().permitAll();



        });

        //security.httpBasic(Customizer.withDefaults());
        security.exceptionHandling(ex->ex.authenticationEntryPoint(entryPoint));
        security.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
