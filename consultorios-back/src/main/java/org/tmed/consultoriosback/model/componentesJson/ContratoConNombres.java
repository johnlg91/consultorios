package org.tmed.consultoriosback.model.componentesJson;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@JsonComponent
public class ContratoConNombres {
    @Id
    private long id;
    private long numeroDeConsultorio;
    private String sobrenombre;
    private String tipoDeAlquiler;

    private java.sql.Date inicioDelContratoDeAlquiler;
    private java.sql.Date finDelContrato;
    private long costoTotal;
    private String notas;
    private boolean oculto;

    public ContratoConNombres() {
    }

    public ContratoConNombres(long id, long numeroDeConsultorio, String sobrenombre, String tipoDeAlquiler, Date inicioDelContratoDeAlquiler, Date finDelContrato, long costoTotal, String notas, boolean oculto) {
        this.id = id;
        this.numeroDeConsultorio = numeroDeConsultorio;
        this.sobrenombre = sobrenombre;
        this.tipoDeAlquiler = tipoDeAlquiler;
        this.inicioDelContratoDeAlquiler = inicioDelContratoDeAlquiler;
        this.finDelContrato = finDelContrato;
        this.costoTotal = costoTotal;
        this.notas = notas;
        this.oculto = oculto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumeroDeConsultorio() {
        return numeroDeConsultorio;
    }

    public void setNumeroDeConsultorio(long numeroDeConsultorio) {
        this.numeroDeConsultorio = numeroDeConsultorio;
    }

    public String getSobrenombre() {
        return sobrenombre;
    }

    public void setSobrenombre(String sobrenombre) {
        this.sobrenombre = sobrenombre;
    }

    public String getTipoDeAlquiler() {
        return tipoDeAlquiler;
    }

    public void setTipoDeAlquiler(String tipoDeAlquiler) {
        this.tipoDeAlquiler = tipoDeAlquiler;
    }

    public Date getInicioDelContratoDeAlquiler() {
        return inicioDelContratoDeAlquiler;
    }

    public void setInicioDelContratoDeAlquiler(Date inicioDelContratoDeAlquiler) {
        this.inicioDelContratoDeAlquiler = inicioDelContratoDeAlquiler;
    }

    public Date getFinDelContrato() {
        return finDelContrato;
    }

    public void setFinDelContrato(Date finDelContrato) {
        this.finDelContrato = finDelContrato;
    }

    public long getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(long costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
}
