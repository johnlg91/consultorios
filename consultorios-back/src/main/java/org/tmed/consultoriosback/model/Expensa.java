package org.tmed.consultoriosback.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("EXPENSAS")
public class Expensa {
    @Id
    private long id;
    private String descripcion;
    private java.sql.Date fechaDeExpensa;
    private double cantidad;
    private String seRepite;
    private java.sql.Date fechaDePago;
    private boolean oculto;

    public Expensa() {
    }

    public Expensa(long id, String descripcion, Date fechaDeExpensa, double cantidad, String seRepite, Date fechaDePago, boolean oculto) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaDeExpensa = fechaDeExpensa;
        this.cantidad = cantidad;
        this.seRepite = seRepite;
        this.fechaDePago = fechaDePago;
        this.oculto = oculto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public java.sql.Date getFechaDeExpensa() {
        return fechaDeExpensa;
    }

    public void setFechaDeExpensa(java.sql.Date fechaDeExpensa) {
        this.fechaDeExpensa = fechaDeExpensa;
    }


    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }


    public String getSeRepite() {
        return seRepite;
    }

    public void setSeRepite(String seRepite) {
        this.seRepite = seRepite;
    }


    public java.sql.Date getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(java.sql.Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }


    public boolean getOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

}
