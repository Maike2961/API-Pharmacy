package io.github.farmacia.Farmacia.db.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.github.farmacia.Farmacia.db.security.CustomUserDetailsService;
import io.github.farmacia.Farmacia.db.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(t -> {
                    t.requestMatchers(HttpMethod.POST, "/usuario/**").permitAll();
                    t.requestMatchers(HttpMethod.POST, "/fornecedor/**").hasRole("ADMIN");
                    t.requestMatchers(HttpMethod.PUT, "/fornecedor/**").hasRole("ADMIN");
                    t.requestMatchers(HttpMethod.DELETE, "/fornecedor/**").hasRole("ADMIN");
                    t.requestMatchers("/item/**").hasAnyRole("USER", "ADMIN");
                    t.anyRequest().authenticated();
                })
                .build();
    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService service) {
        return new CustomUserDetailsService(service);

    }
}
