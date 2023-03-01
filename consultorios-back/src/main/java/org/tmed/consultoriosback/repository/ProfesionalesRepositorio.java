package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Profesional;

import java.util.Optional;

@Repository
public interface ProfesionalesRepositorio extends CrudRepository<Profesional, Long> {

    @Query("SELECT * FROM PROFESIONALES WHERE OCULTO = 0")
    Iterable<Profesional> getProfesionales();

    @Query("SELECT * FROM PROFESIONALES WHERE DNI = :dni AND OCULTO = 0")
    Optional<Profesional> findByDni(@Param("dni") long dni);

    @Modifying
    @Query("UPDATE PROFESIONALES SET OCULTO = 1 WHERE ID = :id")
    void deleteProfesional(@Param("id") long id);

}
