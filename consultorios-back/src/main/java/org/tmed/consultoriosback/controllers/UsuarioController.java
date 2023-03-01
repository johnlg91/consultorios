package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Usuario;
import org.tmed.consultoriosback.repository.UsuariosRepositorio;

@RestController
public class UsuarioController {
    final UsuariosRepositorio usuariosRepositorio;

    @Autowired
    public UsuarioController(UsuariosRepositorio usuariosRepositorio) {
        this.usuariosRepositorio = usuariosRepositorio;
    }

    @GetMapping("/usuarios/all")
    public Iterable<Usuario> getAllUsuarioss() {
        return usuariosRepositorio.findAll();
    }

    @GetMapping("/usuarios")
    public Iterable<Usuario> getUsuarios() {
        return usuariosRepositorio.getUsuario();
    }

    @GetMapping(value = "/usuarios/{id}")
    public Usuario getUsuarios(@PathVariable("id") long id) {
        return usuariosRepositorio.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping("/usuarios")
    public Usuario postUsuarios(@Validated @RequestBody Usuario usuario) {
        return saveUsuario(usuario);
    }

    @PutMapping(value = "/usuarios")
    public Usuario putUsuarios(@Validated @RequestBody Usuario usuario) {
        if (usuariosRepositorio.existsById(usuario.getId())) return saveUsuario(usuario);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + usuario.getId() + " not found.");
    }

    @DeleteMapping("/usuarios/{id}")
    public void deleteUsuarios(@PathVariable("id") long id) {
        usuariosRepositorio.deleteUsuario(id);
    }

    private Usuario saveUsuario(Usuario usuario) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        usuario.setContrasennia(encoder.encode(usuario.getContrasennia()));
        return usuariosRepositorio.save(usuario);
    }
}
