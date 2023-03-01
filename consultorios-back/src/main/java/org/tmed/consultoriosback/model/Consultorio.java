package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("CONSULTORIOS")
public class Consultorio {

    @Id
    private long id;
    private long numeroDeConsultorio;
    private long costoPorModulo;
    private long tamanioDelArea;
    private String equipo;
    private String especialidades;
    private boolean oculto;

    public Consultorio() {
    }

    public Consultorio(long id, long numeroDeConsultorio, long costoPorModulo, long tamanioDelArea, String equipo, String especialidades, boolean oculto) {
        this.id = id;
        this.numeroDeConsultorio = numeroDeConsultorio;
        this.costoPorModulo = costoPorModulo;
        this.tamanioDelArea = tamanioDelArea;
        this.equipo = equipo;
        this.especialidades = especialidades;
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


    public long getCostoPorModulo() {
        return costoPorModulo;
    }

    public void setCostoPorModulo(long costoPorModulo) {
        this.costoPorModulo = costoPorModulo;
    }


    public long getTamanioDelArea() {
        return tamanioDelArea;
    }

    public void setTamanioDelArea(long tamanioDelArea) {
        this.tamanioDelArea = tamanioDelArea;
    }


    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }


    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
}
