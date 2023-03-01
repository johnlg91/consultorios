package org.tmed.consultoriosback.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.tmed.consultoriosback.repository.UsuariosRepositorio;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UsuariosRepositorio usuarioRep;
    @Autowired
    public WebSecurityConfig(UsuariosRepositorio usuarioRep) {
        this.usuarioRep = usuarioRep;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
                .formLogin((form) -> form.loginPage("/login").permitAll())
                .logout((logout) -> logout.permitAll())
                .csrf().disable();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new ServicioDeUsuarioDeSeguridad(usuarioRep);
    }
}