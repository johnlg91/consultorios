package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tmed.consultoriosback.model.componentesJson.ReporteVacancia;
import org.tmed.consultoriosback.repository.AlquileresVacanciaRepositorio;
import org.tmed.consultoriosback.repository.ExpensasRepositorio;
import org.tmed.consultoriosback.repository.TransaccionesDeAlquilerRepositorio;

import java.sql.Date;
import java.util.List;

@RestController
public class ReportesController {
    final TransaccionesDeAlquilerRepositorio transaccionesRep;
    final ExpensasRepositorio expensasRep;

    final AlquileresVacanciaRepositorio acvRep;

    @Autowired
    public ReportesController(
            TransaccionesDeAlquilerRepositorio transaccionesRep,
            ExpensasRepositorio expensasRep,
            AlquileresVacanciaRepositorio acvRep) {
        this.transaccionesRep = transaccionesRep;
        this.expensasRep = expensasRep;
        this.acvRep = acvRep;
    }

    @GetMapping("/reportes/ingresos")
    public List<Double> getIngresosPorMes(
            @Validated
            @RequestParam(name = "fecha1") String fecha1,
            @RequestParam(name = "fecha2") String fecha2) {
        return List.of(transaccionesRep.getIngresosPorMes(fecha1).orElse(0.0),
                transaccionesRep.getIngresosPorMes(fecha2).orElse(0.0));
    }

    @GetMapping("/reportes/egresos")
    public List<Double> getEgresosPorMes(
            @Validated
            @RequestParam(name = "fecha1") String fecha1,
            @RequestParam(name = "fecha2") String fecha2) {
        return List.of(expensasRep.getExoensasPorMes(fecha1).orElse(0.0),
                expensasRep.getExoensasPorMes(fecha2).orElse(0.0));
    }

    @GetMapping("/reportes/vacancias")
    public Iterable<ReporteVacancia> getReportesVacancia(
            @Validated
            @RequestParam(name = "fecha1") String fecha1,
            @RequestParam(name = "fecha2") String fecha2) {
        return acvRep.getReporteVacancia(Date.valueOf(fecha1), Date.valueOf(fecha2));
//        return List.of(
//                new ReporteVacancia(1,10, 100, 25 ),
//                new ReporteVacancia(2,20, 50, 30 )
//        );
    }
}
