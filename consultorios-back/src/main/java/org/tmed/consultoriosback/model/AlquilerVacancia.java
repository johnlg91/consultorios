package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.tmed.consultoriosback.model.componentesJson.DiaDeLaSemana;

import java.sql.Time;

@Table("ALQUILERES_VACANCIA")
public class AlquilerVacancia {

    @Id
    private long id;
    private long idContratoDeAlquiler;
    private String diaDeLaSemana;
    private java.sql.Time empiezaVacancia;
    private java.sql.Time terminaVacancia;
    private boolean oculto;

    public AlquilerVacancia() {}

    public AlquilerVacancia(long id, long idContratoDeAlquiler, DiaDeLaSemana diaDeLaSemana, Time empiezaVacancia, Time terminaVacancia, boolean oculto) {
        this.id = id;
        this.idContratoDeAlquiler = idContratoDeAlquiler;
        this.diaDeLaSemana = diaDeLaSemana.name();
        this.empiezaVacancia = empiezaVacancia;
        this.terminaVacancia = terminaVacancia;
        this.oculto = oculto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getIdContratoDeAlquiler() {
        return idContratoDeAlquiler;
    }

    public void setIdContratoDeAlquiler(long idContratoDeAlquiler) {
        this.idContratoDeAlquiler = idContratoDeAlquiler;
    }


    public DiaDeLaSemana getDiaDeLaSemana() {
        return DiaDeLaSemana.valueOf(diaDeLaSemana);
    }

    public void setDiaDeLaSemana(DiaDeLaSemana diaDeLaSemana) {
        this.diaDeLaSemana = diaDeLaSemana.name();
    }


    public java.sql.Time getEmpiezaVacancia() {
        return empiezaVacancia;
    }

    public void setEmpiezaVacancia(java.sql.Time empiezaVacancia) {
        this.empiezaVacancia = empiezaVacancia;
    }


    public java.sql.Time getTerminaVacancia() {
        return terminaVacancia;
    }

    public void setTerminaVacancia(java.sql.Time terminaVacancia) {
        this.terminaVacancia = terminaVacancia;
    }


    public void setDiaDeLaSemana(String diaDeLaSemana) {
        this.diaDeLaSemana = diaDeLaSemana;
    }

    public boolean getOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
}
