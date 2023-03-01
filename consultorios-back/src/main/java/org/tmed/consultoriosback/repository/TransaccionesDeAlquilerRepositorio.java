package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.TransaccionDeAlquiler;

import java.util.Optional;

@Repository
public interface TransaccionesDeAlquilerRepositorio extends CrudRepository<TransaccionDeAlquiler, Long> {

    @Query("SELECT * FROM CONSULTORIOS WHERE OCULTO = 0")
    Iterable<TransaccionDeAlquiler> getTransaccionDeAlquiler();

    @Query("""
            select  sum(TDA.CANTIDAD)
            from TRANSACCIONES_DE_ALQUILERES TDA
            where MONTH(TDA.FECHA_DE_TRANSACCION) = MONTH(:mes) and
                    YEAR(TDA.FECHA_DE_TRANSACCION) = YEAR(:mes)
    """)
    Optional<Double> getIngresosPorMes(@Param("mes") String mes);

    @Modifying
    @Query("UPDATE CONSULTORIOS SET OCULTO = 1 WHERE ID = :id")
    void deleteTransaccionDeAlquiler(@Param("id") long id);
}

