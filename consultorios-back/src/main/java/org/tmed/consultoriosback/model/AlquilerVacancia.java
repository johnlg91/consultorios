package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;

@Table("ALQUILERES_VACANCIA")
public record AlquilerVacancia(@Id long id, long idContratoDeAlquiler, String diaDeLaSemana,
                               Time empiezaVacancia, Time terminaVacancia, boolean oculto
) {
}