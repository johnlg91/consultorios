package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.ContratoDeAlquiler;
import org.tmed.consultoriosback.model.DTO.ContratoSinPagar;
import org.tmed.consultoriosback.model.componentesJson.ContratoConNombres;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ContratosDeAlquilerRepositorio extends CrudRepository<ContratoDeAlquiler, Long> {

    @Query("SELECT * FROM CONTRATOS_DE_ALQUILER WHERE OCULTO = 0")
    Iterable<ContratoDeAlquiler> getContratos();

    @Query("""
            SELECT CDA.ID,
                    C.NUMERO_DE_CONSULTORIO,
                    PROFESIONALES.SOBRENOMBRE,
                    CDA.TIPO_DE_ALQUILER,
                    CDA.INICIO_DEL_CONTRATO_DE_ALQUILER,
                    CDA.FIN_DEL_CONTRATO,
                    CDA.COSTO_POR_MODULO,
                    CDA.MONTO_A_PAGAR,
                    CDA.NOTAS
            FROM ((CONTRATOS_DE_ALQUILER CDA
            INNER JOIN CONSULTORIOS C ON CDA.ID_CONSULTORIO = C.ID)
            INNER JOIN PROFESIONALES ON CDA.ID_PROFESIONAL = PROFESIONALES.ID)
            WHERE CDA.OCULTO = 0
            """)
    Iterable<ContratoConNombres> getContratosConNombres();

    @Query("""
               SELECT ID
               FROM CONTRATOS_DE_ALQUILER
               WHERE  ID_CONSULTORIO = :consultorio
                   and ID_PROFESIONAL = :profesional
                   and INICIO_DEL_CONTRATO_DE_ALQUILER <= :fechaFin
                   and FIN_DEL_CONTRATO >= :fechaInicio
                   and OCULTO = 0
               LIMIT 1
            """)
    Optional<Integer> existsContractForRange(@Param("consultorio") long consultorio,
                                             @Param("profesional") long profesional,
                                             @Param("fechaInicio") Date fechaInicio,
                                             @Param("fechaFin") Date fechaFin);

    @Query("""
            SELECT
                CDA.ID,
                C.NUMERO_DE_CONSULTORIO,
                P.SOBRENOMBRE,
                CDA.TIPO_DE_ALQUILER,
                CDA.INICIO_DEL_CONTRATO_DE_ALQUILER,
                CDA.FIN_DEL_CONTRATO,
                CDA.COSTO_POR_MODULO,
                CDA.MONTO_A_PAGAR,
                CDA.NOTAS
            FROM CONTRATOS_DE_ALQUILER CDA
              INNER JOIN CONSULTORIOS C ON CDA.ID_CONSULTORIO = C.ID
              INNER JOIN PROFESIONALES P ON CDA.ID_CONSULTORIO = P.ID
              WHERE :fecha BETWEEN INICIO_DEL_CONTRATO_DE_ALQUILER AND FIN_DEL_CONTRATO
              AND C.NUMERO_DE_CONSULTORIO = :numConsultorio
              AND C.OCULTO = 0
              AND CDA.OCULTO = 0
            """)
    Iterable<ContratoConNombres> getContratosPorNumeroDeConsultorio(@Param("fecha") String fecha, @Param("numConsultorio") long numConsultorio);

    @Query(""" 
                SELECT
                    cda.ID,
                    cda.ID_CONSULTORIO,
                    cda.ID_PROFESIONAL,
                    cda.COSTO_POR_MODULO,
                    COALESCE(cda.COSTO_POR_MODULO * TIMESTAMPDIFF(MONTH, cda.INICIO_DEL_CONTRATO_DE_ALQUILER, cda.FIN_DEL_CONTRATO), 0) AS valor_contrato,
                    (cda.COSTO_POR_MODULO * TIMESTAMPDIFF(MONTH, cda.INICIO_DEL_CONTRATO_DE_ALQUILER, cda.FIN_DEL_CONTRATO)) - COALESCE(SUM(tda.CANTIDAD), 0) AS monto_restante,
                    cda.INICIO_DEL_CONTRATO_DE_ALQUILER,
                    cda.FIN_DEL_CONTRATO
                    FROM
                        CONTRATOS_DE_ALQUILER cda
                            LEFT JOIN TRANSACCIONES_DE_ALQUILERES tda ON cda.ID = tda.ID_CONTRATO_DE_ALQUILER
                                AND month(tda.FECHA_DE_TRANSACCION) = month(current_date())
                                AND year(tda.FECHA_DE_TRANSACCION) = year(current_date())
                    WHERE
                        tda.ID IS NULL
                        AND cda.OCULTO = 0
                        AND cda.FIN_DEL_CONTRATO >= current_date()
                    GROUP BY cda.ID
            """)
    Iterable<ContratoSinPagar> getContratosSinPagar();

    @Query("SELECT * FROM CONTRATOS_DE_ALQUILER WHERE TIPO_DE_ALQUILER = 'NORMAL'")
    Iterable<ContratoDeAlquiler> getContratosNormales();

    @Query("SELECT * FROM CONTRATOS_DE_ALQUILER WHERE TIPO_DE_ALQUILER = 'EXCEPCIONAL'")
    Iterable<ContratoDeAlquiler> getContratosExcepcionales();

    @Modifying
    @Query("UPDATE CONTRATOS_DE_ALQUILER SET OCULTO = 1 WHERE ID = :id")
    void deleteContrato(@Param("id") long id);
}
