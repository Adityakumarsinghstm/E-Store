package com.aditya.electronic.store.config;

import com.aditya.electronic.store.security.JwtAuthenticationEntryPoint;
import com.aditya.electronic.store.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

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
        //security.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());

        //cors enable
        security.cors(httpSecurityCorsConfigurer ->httpSecurityCorsConfigurer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();

                corsConfiguration.setAllowedHeaders(List.of("Authorization"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setMaxAge(3000L);

                return corsConfiguration;
            }
        }));


        //csrf disable
        security.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());


        security.authorizeHttpRequests(request->{
            request
                    .requestMatchers(HttpMethod.DELETE,"/users/**").hasRole(AppConstants.ROLE_ADMIN)
                    .requestMatchers(HttpMethod.PUT,"/users/**").hasAnyRole(AppConstants.ROLE_ADMIN,AppConstants.ROLE_NORMAL)

                    .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
                    .requestMatchers("/products/**").hasRole(AppConstants.ROLE_ADMIN)
                    .requestMatchers(HttpMethod.GET,"/users/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/users").permitAll()
                    .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
                    .requestMatchers("/categories/**").hasRole(AppConstants.ROLE_ADMIN)
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
