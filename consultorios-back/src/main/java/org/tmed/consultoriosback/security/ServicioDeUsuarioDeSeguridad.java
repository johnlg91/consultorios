package org.tmed.consultoriosback.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.tmed.consultoriosback.model.Usuario;
import org.tmed.consultoriosback.repository.UsuariosRepositorio;

import java.util.Optional;

public class ServicioDeUsuarioDeSeguridad implements UserDetailsService {
    private final UsuariosRepositorio usuarioRep;
    public ServicioDeUsuarioDeSeguridad(UsuariosRepositorio usuarioRep) {
        this.usuarioRep = usuarioRep;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) {
        final Optional<Usuario> usuario = usuarioRep.findByNombreDeUsuario(nombreUsuario);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException(nombreUsuario);
        }
        return new SpringSecurityUserDetails(usuario.get());
    }
}
