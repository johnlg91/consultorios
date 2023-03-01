package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.ContratoDeAlquiler;
import org.tmed.consultoriosback.model.componentesJson.ContratoConNombres;
import org.tmed.consultoriosback.repository.ContratosDeAlquilerRepositorio;
import org.tmed.consultoriosback.repository.TransaccionesDeAlquilerRepositorio;

@RestController
public class ContratosController {

    private final ContratosDeAlquilerRepositorio contratosRep;
    private final TransaccionesDeAlquilerRepositorio transaccionesDeAlquilerRep;


    @Autowired
    public ContratosController(ContratosDeAlquilerRepositorio contratosRep, TransaccionesDeAlquilerRepositorio transaccionesDeAlquilerRep) {
        this.transaccionesDeAlquilerRep = transaccionesDeAlquilerRep;
        this.contratosRep = contratosRep;
    }

    @GetMapping("/contratos/all")
    public Iterable<ContratoDeAlquiler> getAllContratos() {
        return contratosRep.findAll();
    }

    @GetMapping("/contratos/nombres")
    public Iterable<ContratoConNombres> getContratosConNombres() {
        return contratosRep.getContratosConNombres();
    }

    @GetMapping("/contratos")
    public Iterable<ContratoDeAlquiler> getContratos() {
        return contratosRep.getContratos();
    }

    @GetMapping("/contratos/porconsultorio")
    public Iterable<ContratoConNombres> getContratosPorNumeroDeConsultorio(
            @Validated
            @RequestParam(name = "fecha") String fecha,
            @RequestParam(name = "numConsultorio") long numConsultorio) {
        return contratosRep.getContratosPorNumeroDeConsultorio(fecha, numConsultorio);
    }

    @GetMapping("/contratos/apagar")
    public Iterable<ContratoDeAlquiler> getContratosSinPagar(
            @Validated
            @RequestParam(name = "fecha") String fecha) {
        return contratosRep.getContratosSinPagar(fecha);
    }

    @GetMapping("/contratos/excepcionales")
    public Iterable<ContratoDeAlquiler> getContratosExcepcionales() {
        return contratosRep.getContratosExcepcionales();
    }

    @GetMapping("/contratos/normales")
    public Iterable<ContratoDeAlquiler> getContratosNormales() {
        return contratosRep.getContratosNormales();
    }

    @GetMapping(value = "/contratos/{id}")
    public ContratoDeAlquiler getContrato(@PathVariable("id") long id) {
        return contratosRep.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping("/contratos")
    public ContratoDeAlquiler postContrato(@Validated @RequestBody ContratoDeAlquiler contrato) {
        return contratosRep.save(contrato);
    }

    @PutMapping(value = "/contratos")
    public ContratoDeAlquiler putContratos(@Validated @RequestBody ContratoDeAlquiler contrato) {
        if (contratosRep.existsById(contrato.getId())) return contratosRep.save(contrato);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + contrato.getId() + " not found.");
    }

    @DeleteMapping("/contratos/{id}")
    public void deleteContratos(@PathVariable("id") long id) {
        contratosRep.deleteContrato(id);
    }
}
