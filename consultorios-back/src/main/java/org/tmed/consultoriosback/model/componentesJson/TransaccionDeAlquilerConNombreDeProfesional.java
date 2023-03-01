package org.tmed.consultoriosback.model.componentesJson;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@JsonComponent
public class TransaccionDeAlquilerConNombreDeProfesional {
    @Id
    private long id;
    private long idProfesional;
    private String sobrenombreProfesional;
    private java.sql.Date fechaDeTransaccion;
    private String tipo;
    private String metodoDePago;
    private double cantidad;
    private boolean oculto;

    public TransaccionDeAlquilerConNombreDeProfesional(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public String getSobrenombreProfesional() {
        return sobrenombreProfesional;
    }

    public void setSobrenombreProfesional(String sobrenombreProfesional) {
        this.sobrenombreProfesional = sobrenombreProfesional;
    }

    public Date getFechaDeTransaccion() {
        return fechaDeTransaccion;
    }

    public void setFechaDeTransaccion(Date fechaDeTransaccion) {
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

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
}
