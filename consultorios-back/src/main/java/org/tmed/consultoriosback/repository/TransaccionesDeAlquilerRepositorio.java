package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.TransaccionDeAlquiler;
import org.tmed.consultoriosback.model.componentesJson.ContratoConCosto;

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

    @Query("""
            SELECT
                c.ID AS ID_CONTRATO,
                cons.NUMERO_DE_CONSULTORIO,
                p.NOMBRE AS NOMBRE_PROFESIONAL,
                p.APELLIDO,
                c.COSTO_POR_MODULO * TIMESTAMPDIFF(MONTH, c.INICIO_DEL_CONTRATO_DE_ALQUILER, c.FIN_DEL_CONTRATO) AS valor_contrato,
                (c.COSTO_POR_MODULO * TIMESTAMPDIFF(MONTH, c.INICIO_DEL_CONTRATO_DE_ALQUILER, c.FIN_DEL_CONTRATO)) - COALESCE(SUM(t.CANTIDAD), 0) AS monto_restante
            FROM
                CONTRATOS_DE_ALQUILER c
                JOIN PROFESIONALES p ON c.ID_PROFESIONAL = p.ID
                INNER JOIN CONSULTORIOS cons ON c.ID_CONSULTORIO = cons.ID
                LEFT JOIN TRANSACCIONES_DE_ALQUILERES t ON c.ID = t.ID_CONTRATO_DE_ALQUILER
            WHERE
                    c.OCULTO = 0
            GROUP BY
                c.FIN_DEL_CONTRATO, c.ID, p.NOMBRE, c.COSTO_POR_MODULO, c.INICIO_DEL_CONTRATO_DE_ALQUILER

            """)
    Iterable<ContratoConCosto> getContratosConPagos();

    @Modifying
    @Query("UPDATE CONSULTORIOS SET OCULTO = 1 WHERE ID = :id")
    void deleteTransaccionDeAlquiler(@Param("id") long id);
}

