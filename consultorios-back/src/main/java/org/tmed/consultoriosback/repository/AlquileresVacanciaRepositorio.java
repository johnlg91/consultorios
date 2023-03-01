package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.AlquilerVacancia;
import org.tmed.consultoriosback.model.componentesJson.CoordenadaDeMatriz;
import org.tmed.consultoriosback.model.componentesJson.ReporteVacancia;

import java.sql.Date;

@Repository
public interface AlquileresVacanciaRepositorio extends CrudRepository<AlquilerVacancia, Long> {
    @Query("""
            SELECT
                AV.ID as ID_ALQUILER_VACANCIA,
                AV.DIA_DE_LA_SEMANA,
                AV.EMPIEZA_VACANCIA,
                AV.TERMINA_VACANCIA,
                C.NUMERO_DE_CONSULTORIO,
                P.NOMBRE, P.APELLIDO,
                ID_CONTRATO_DE_ALQUILER
                      FROM ALQUILERES_VACANCIA AV
                           JOIN CONTRATOS_DE_ALQUILER CDA on AV.ID_CONTRATO_DE_ALQUILER = CDA.ID
                           JOIN CONSULTORIOS C on C.ID = CDA.ID_CONSULTORIO
                           JOIN PROFESIONALES P on CDA.ID_PROFESIONAL = P.ID
                           WHERE CDA.FIN_DEL_CONTRATO >= :inicio AND
                                 CDA.INICIO_DEL_CONTRATO_DE_ALQUILER <= :fin AND
                                 CDA.OCULTO = 0
                """)
    Iterable<CoordenadaDeMatriz> getAlquileresVacanciaParaMatriz(@Param("inicio") Date inicio, @Param("fin") Date fin);

    @Query("""
            select C.ID,
                   TIMESTAMPDIFF(DAY,
                       :inicio,
                       :fin) * (22-8) TOT,
                   sum(TIMESTAMPDIFF(HOUR, AV.EMPIEZA_VACANCIA,
                                           AV.TERMINA_VACANCIA)) OCUP
                from CONSULTORIOS C
                left outer join CONTRATOS_DE_ALQUILER CDA on C.ID = CDA.ID_CONSULTORIO
                join ALQUILERES_VACANCIA AV on CDA.ID = AV.ID_CONTRATO_DE_ALQUILER
                WHERE CDA.FIN_DEL_CONTRATO >= :inicio
                    AND CDA.INICIO_DEL_CONTRATO_DE_ALQUILER <= :fin
                    AND CDA.OCULTO = 0
                    AND C.OCULTO = 0
                group by C.ID
            """)
    Iterable<ReporteVacancia> getReporteVacancia(@Param("inicio") Date inicio, @Param("fin") Date fin);
}
