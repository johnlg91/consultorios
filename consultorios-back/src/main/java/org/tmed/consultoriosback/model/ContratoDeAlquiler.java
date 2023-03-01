package org.tmed.consultoriosback.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("CONTRATOS_DE_ALQUILER")
public class ContratoDeAlquiler {

    @Id
    private long id;
    private long idConsultorio;
    private long idProfesional;
    private String tipoDeAlquiler;

    private java.sql.Date inicioDelContratoDeAlquiler;
    private java.sql.Date finDelContrato;
    private long costoTotal;
    private String notas;
    private boolean oculto;

    public ContratoDeAlquiler() {
    }

    public ContratoDeAlquiler(long id, long idConsultorio, long idProfesional, String tipoDeAlquiler, Date inicioDelContratoDeAlquiler, Date finDelContrato, long costoTotal, String notas, boolean oculto) {
        this.id = id;
        this.idConsultorio = idConsultorio;
        this.idProfesional = idProfesional;
        this.tipoDeAlquiler = tipoDeAlquiler;
        this.inicioDelContratoDeAlquiler = inicioDelContratoDeAlquiler;
        this.finDelContrato = finDelContrato == null ? inicioDelContratoDeAlquiler : finDelContrato;
        this.costoTotal = costoTotal;
        this.notas = notas;
        this.oculto = oculto;
    }

    public Date getInicioDelContratoDeAlquiler() {
        return inicioDelContratoDeAlquiler;
    }

    public void setInicioDelContratoDeAlquiler(Date inicioDelContratoDeAlquiler) {
        this.inicioDelContratoDeAlquiler = inicioDelContratoDeAlquiler;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(long idConsultorio) {
        this.idConsultorio = idConsultorio;
    }


    public long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(long idProfesional) {
        this.idProfesional = idProfesional;
    }


    public String getTipoDeAlquiler() {
        return tipoDeAlquiler;
    }

    public void setTipoDeAlquiler(String tipoDeAlquiler) {
        this.tipoDeAlquiler = tipoDeAlquiler;
    }


    public java.sql.Date getFinDelContrato() {
        return finDelContrato;
    }

    public void setFinDelContrato(java.sql.Date finDelContrato) {
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


    public boolean getOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

}
