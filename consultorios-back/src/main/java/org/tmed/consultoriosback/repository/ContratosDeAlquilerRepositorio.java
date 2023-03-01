package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.ContratoDeAlquiler;
import org.tmed.consultoriosback.model.componentesJson.ContratoConNombres;

import java.util.stream.Stream;

@Repository
public interface ContratosDeAlquilerRepositorio extends CrudRepository<ContratoDeAlquiler, Long> {

    @Query("SELECT * FROM CONTRATOS_DE_ALQUILER WHERE OCULTO = 0")
    Iterable<ContratoDeAlquiler> getContratos();

    @Query("SELECT CONTRATOS_DE_ALQUILER.ID, CONSULTORIOS.NUMERO_DE_CONSULTORIO, PROFESIONALES.SOBRENOMBRE ,TIPO_DE_ALQUILER, INICIO_DEL_CONTRATO_DE_ALQUILER, FIN_DEL_CONTRATO, COSTO_TOTAL, CONTRATOS_DE_ALQUILER.NOTAS " +
            "FROM ((CONTRATOS_DE_ALQUILER " +
            "INNER JOIN CONSULTORIOS ON CONTRATOS_DE_ALQUILER.ID_CONSULTORIO = CONSULTORIOS.ID) " +
            "INNER JOIN PROFESIONALES ON CONTRATOS_DE_ALQUILER.ID_PROFESIONAL = PROFESIONALES.ID) " +
            "WHERE CONTRATOS_DE_ALQUILER.OCULTO = 0")
    Iterable<ContratoConNombres> getContratosConNombres();

    @Query("SELECT * FROM CONTRATOS_DE_ALQUILER WHERE OCULTO = 0 AND INICIO_DEL_CONTRATO_DE_ALQUILER <= :fechaInicio AND FIN_DEL_CONTRATO >= :fechaFin")
    Stream<ContratoDeAlquiler> getContratosEntreFechas(@Param("fechaInicio") java.sql.Date fechaInicio, @Param("fechaFin") java.sql.Date fechaFin);

    @Query("SELECT CDA.ID, C.NUMERO_DE_CONSULTORIO, P.SOBRENOMBRE, TIPO_DE_ALQUILER, INICIO_DEL_CONTRATO_DE_ALQUILER, FIN_DEL_CONTRATO, COSTO_TOTAL, CDA.NOTAS " +
            "FROM CONTRATOS_DE_ALQUILER CDA" +
            "  INNER JOIN CONSULTORIOS C ON CDA.ID_CONSULTORIO = C.ID" +
            "  INNER JOIN PROFESIONALES P ON CDA.ID_CONSULTORIO = P.ID" +
            "  WHERE :fecha BETWEEN INICIO_DEL_CONTRATO_DE_ALQUILER AND FIN_DEL_CONTRATO" +
            "  AND C.NUMERO_DE_CONSULTORIO = :numConsultorio" +
            "  AND C.OCULTO = 0" +
            "  AND CDA.OCULTO = 0")
    Iterable<ContratoConNombres> getContratosPorNumeroDeConsultorio(@Param("fecha") String fecha, @Param("numConsultorio") long numConsultorio);

    @Query("SELECT * FROM CONTRATOS_DE_ALQUILER CDA" +
            "  WHERE OCULTO = 0" +
            "  AND :mes BETWEEN INICIO_DEL_CONTRATO_DE_ALQUILER AND FIN_DEL_CONTRATO" +
            "  AND ID NOT IN (SELECT ID_CONTRATO_DE_ALQUILER" +
            "                 FROM TRANSACCIONES_DE_ALQUILERES TDA" +
            "                 WHERE month(FECHA_DE_TRANSACCION) = month(:mes)" +
            "                   and year(FECHA_DE_TRANSACCION) = year(:mes))")
    Iterable<ContratoDeAlquiler> getContratosSinPagar(@Param("mes") String mes);

    @Query("SELECT * FROM CONTRATOS_DE_ALQUILER WHERE TIPO_DE_ALQUILER = 'NORMAL'")
    Iterable<ContratoDeAlquiler> getContratosNormales();

    @Query("SELECT * FROM CONTRATOS_DE_ALQUILER WHERE TIPO_DE_ALQUILER = 'EXCEPCIONAL'")
    Iterable<ContratoDeAlquiler> getContratosExcepcionales();

    @Modifying
    @Query("UPDATE CONTRATOS_DE_ALQUILER SET OCULTO = 1 WHERE ID = :id")
    void deleteContrato(@Param("id") long id);
}
