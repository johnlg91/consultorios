package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuariosRepositorio extends CrudRepository<Usuario, Long> {

    @Query("SELECT * FROM USUARIOS WHERE OCULTO = 0")
    Iterable<Usuario> getUsuarios();

    @Query("""
        SELECT *
        FROM USUARIOS U
        JOIN CONTRATOS_DE_ALQUILER CDA
        ON U.ID = CDA.ID_PROFESIONAL AND CDA.FIN_DEL_CONTRATO < CURDATE()
        WHERE OCULTO = 0
    """)
    Iterable<Usuario> findActiveUsers();

    @Query("SELECT * FROM USUARIOS WHERE NOMBRE_USUARIO = :usuario AND OCULTO = 0")
    Optional<Usuario> findByNombreDeUsuario(@Param("usuario") String nombreUsuario);

    @Modifying
    @Query("UPDATE USUARIOS SET OCULTO = 1 WHERE ID = :id")
    void deleteUsuario(@Param("id") long id);
}
