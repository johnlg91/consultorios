package org.tmed.consultoriosback.model.componentesJson;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;

@JsonComponent
public class ReporteVacancia {

    @Id
    private int idConsultorio;
    private int numConsultorio;

    private int hTotales;
    private int hOcupadas;

    public ReporteVacancia() {
    }

    public ReporteVacancia(int idConsultorio, int numConsultorio, int hTotales, int hOcupadas) {
        this.idConsultorio = idConsultorio;
        this.numConsultorio = numConsultorio;
        this.hTotales = hTotales;
        this.hOcupadas = hOcupadas;
    }

    public int getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(int idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public int getNumConsultorio() {
        return numConsultorio;
    }

    public void setNumConsultorio(int numConsultorio) {
        this.numConsultorio = numConsultorio;
    }

    public int gethTotales() {
        return hTotales;
    }

    public void sethTotales(int hTotales) {
        this.hTotales = hTotales;
    }

    public int gethOcupadas() {
        return hOcupadas;
    }

    public void sethOcupadas(int hOcupadas) {
        this.hOcupadas = hOcupadas;
    }
}
