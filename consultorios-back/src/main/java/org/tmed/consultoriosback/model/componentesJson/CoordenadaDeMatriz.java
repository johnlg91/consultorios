package org.tmed.consultoriosback.model.componentesJson;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;

import java.sql.Time;

@JsonComponent
public class CoordenadaDeMatriz {

    @Id
    long idAlquilerVacancia;
    DiaDeLaSemana diaDeLaSemana;
    Time empiezaVacancia;
    Time terminaVacancia;
    long numeroDeConsultorio;

    String nombre;

    String apellido;

    long idContratoDeAlquiler;


    public CoordenadaDeMatriz() {
    }

    public CoordenadaDeMatriz(long idAlquilerVacancia, DiaDeLaSemana diaDeLaSemana, Time empiezaVacancia, Time terminaVacancia, long numeroDeConsultorio, String nombre, String apellido, long idContratoDeAlquiler) {
        this.idAlquilerVacancia = idAlquilerVacancia;
        this.diaDeLaSemana = diaDeLaSemana;
        this.empiezaVacancia = empiezaVacancia;
        this.terminaVacancia = terminaVacancia;
        this.numeroDeConsultorio = numeroDeConsultorio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idContratoDeAlquiler = idContratoDeAlquiler;
    }

    public long getIdAlquilerVacancia() {
        return idAlquilerVacancia;
    }

    public void setIdAlquilerVacancia(long idAlquilerVacancia) {
        this.idAlquilerVacancia = idAlquilerVacancia;
    }

    public Time getEmpiezaVacancia() {
        return empiezaVacancia;
    }

    public void setEmpiezaVacancia(Time empiezaVacancia) {
        this.empiezaVacancia = empiezaVacancia;
    }

    public Time getTerminaVacancia() {
        return terminaVacancia;
    }

    public void setTerminaVacancia(Time terminaVacancia) {
        this.terminaVacancia = terminaVacancia;
    }

    public DiaDeLaSemana getDiaDeLaSemana() {
        return diaDeLaSemana;
    }

    public void setDiaDeLaSemana(DiaDeLaSemana diaDeLaSemana) {
        this.diaDeLaSemana = diaDeLaSemana;
    }

    public long getNumeroDeConsultorio() {
        return numeroDeConsultorio;
    }

    public void setNumeroDeConsultorio(long numeroDeConsultorio) {
        this.numeroDeConsultorio = numeroDeConsultorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getIdContratoDeAlquiler() {
        return idContratoDeAlquiler;
    }

    public void setIdContratoDeAlquiler(long idContratoDeAlquiler) {
        this.idContratoDeAlquiler = idContratoDeAlquiler;
    }
}
