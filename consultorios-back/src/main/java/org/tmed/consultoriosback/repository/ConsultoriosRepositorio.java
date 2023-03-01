package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Consultorio;

@Repository
public interface ConsultoriosRepositorio extends CrudRepository<Consultorio, Long> {

    @Query("SELECT * FROM CONSULTORIOS WHERE OCULTO = 0")
    Iterable<Consultorio> getConsultorios();

    @Modifying
    @Query("UPDATE CONSULTORIOS SET IMAGENES = :imagen WHERE ID = :id")
    void updateImage(@Param("id") long id, @Param("imagen") byte[] image);

    @Modifying
    @Query("UPDATE CONSULTORIOS SET OCULTO = 1 WHERE ID = :id")
    void deleteConsultorio(@Param("id") long id);
}
