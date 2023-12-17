package org.tmed.consultoriosback.model.componentesJson;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;

@JsonComponent
public class ContratoConCosto {

    @Id
    private long idContrato;

    private long numeroDeConsultorio;
    private String nombreProfesional;
    private String apellido;
    private long valorContrato;
    private long montoRestante;

    public ContratoConCosto() {
    }

    public ContratoConCosto(long idContrato, long numeroDeConsultorio, String nombreProfesional, String apellido, long valorContrato, long montoRestante) {
        this.idContrato = idContrato;
        this.numeroDeConsultorio = numeroDeConsultorio;
        this.nombreProfesional = nombreProfesional;
        this.apellido = apellido;
        this.valorContrato = valorContrato;
        this.montoRestante = montoRestante;
    }

    public long getNumeroDeConsultorio() {
        return numeroDeConsultorio;
    }

    public void setNumeroDeConsultorio(long numeroDeConsultorio) {
        this.numeroDeConsultorio = numeroDeConsultorio;
    }

    public long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(long idContrato) {
        this.idContrato = idContrato;
    }

    public String getNombreProfesional() {
        return nombreProfesional;
    }

    public void setNombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getValorContrato() {
        return valorContrato;
    }

    public void setValorContrato(long valorContrato) {
        this.valorContrato = valorContrato;
    }

    public long getMontoRestante() {
        return montoRestante;
    }

    public void setMontoRestante(long montoRestante) {
        this.montoRestante = montoRestante;
    }
}
