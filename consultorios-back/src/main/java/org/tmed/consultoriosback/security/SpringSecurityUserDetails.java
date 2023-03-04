package org.tmed.consultoriosback.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tmed.consultoriosback.model.Usuario;

import java.util.Collection;
import java.util.Collections;

public class SpringSecurityUserDetails implements UserDetails {
    private final Usuario usuario;

    public SpringSecurityUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.contrasennia();
    }

    @Override
    public String getUsername() {
        return usuario.nombreUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !usuario.oculto();
    }
}
