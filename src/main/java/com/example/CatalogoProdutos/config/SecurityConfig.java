package com.example.CatalogoProdutos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Adicione as novas rotas aqui para permitir o acesso
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/imagens/**", "/cadastro-produto", "/cadastrar-produto").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                )
                // Desabilitar CSRF para simplificar o formulário de upload. Em produção, use uma estratégia mais segura.
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}