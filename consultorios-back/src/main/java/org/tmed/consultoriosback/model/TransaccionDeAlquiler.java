package org.tmed.consultoriosback.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("TRANSACCIONES_DE_ALQUILERES")
public class TransaccionDeAlquiler {
    @Id
    private long id;
    private long idContratoDeAlquiler;
    private java.sql.Date fechaDeTransaccion;
    private String tipo;
    private String metodoDePago;
    private double cantidad;
    private boolean oculto;

    public TransaccionDeAlquiler() {
    }

    public TransaccionDeAlquiler(long idContratoDeAlquiler, Date fechaDeTransaccion, String tipo, String metodoDePago, double cantidad, boolean oculto) {
        this.idContratoDeAlquiler = idContratoDeAlquiler;
        this.fechaDeTransaccion = fechaDeTransaccion;
        this.tipo = tipo;
        this.metodoDePago = metodoDePago;
        this.cantidad = cantidad;
        this.oculto = oculto;
    }

    public TransaccionDeAlquiler(long id, long idContratoDeAlquiler, Date fechaDeTransaccion, String tipo, String metodoDePago, double cantidad, boolean oculto) {
        this.id = id;
        this.idContratoDeAlquiler = idContratoDeAlquiler;
        this.fechaDeTransaccion = fechaDeTransaccion;
        this.tipo = tipo;
        this.metodoDePago = metodoDePago;
        this.cantidad = cantidad;
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


    public java.sql.Date getFechaDeTransaccion() {
        return fechaDeTransaccion;
    }

    public void setFechaDeTransaccion(java.sql.Date fechaDeTransaccion) {
        this.fechaDeTransaccion = fechaDeTransaccion;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }


    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }


    public boolean getOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

}
