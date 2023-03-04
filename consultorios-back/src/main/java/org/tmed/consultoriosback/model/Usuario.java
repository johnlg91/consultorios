package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Table("USUARIOS")
public record Usuario(
        @Id long id,
        long dni,
        String nombreUsuario,
        String email,
        String contrasennia,
        boolean esAdmin,
        boolean oculto
) {
    public Usuario encodePassword() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new Usuario(id, dni, nombreUsuario, email, encoder.encode(contrasennia), esAdmin, oculto);
    }
}