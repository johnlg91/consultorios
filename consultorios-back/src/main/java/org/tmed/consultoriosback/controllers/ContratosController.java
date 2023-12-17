package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.ContratoDeAlquiler;
import org.tmed.consultoriosback.model.DTO.ContratoSinPagar;
import org.tmed.consultoriosback.model.componentesJson.ContratoConNombres;
import org.tmed.consultoriosback.repository.ContratosDeAlquilerRepositorio;

import java.util.Optional;

@RestController
public class ContratosController {

    private final ContratosDeAlquilerRepositorio contratosRep;


    @Autowired
    public ContratosController(ContratosDeAlquilerRepositorio contratosRep) {
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

//    @GetMapping("/contratos/apagar")
//    public Iterable<ContratoDeAlquiler> getContratosSinPagar(
//            @Validated
//            @RequestParam(name = "fecha") String fecha) {
//        return contratosRep.getContratosSinPagar(fecha);
//    }

    @GetMapping("/contratos/apagar")
    public Iterable<ContratoSinPagar> getContratosSinPagar() {
        return contratosRep.getContratosSinPagar();
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
        getContractForRange(contrato).ifPresent(id -> {
            throw new ResponseStatusException(HttpStatus.FOUND, "Contract with Id: " + id + " overlaps.");
        });
        return contratosRep.save(contrato);
    }


    @PutMapping(value = "/contratos")
    public ContratoDeAlquiler putContratos(@Validated @RequestBody ContratoDeAlquiler contrato) {
        if (!contratosRep.existsById(contrato.id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + contrato.id() + " not found.");
        }
        getContractForRange(contrato).ifPresent(id -> {
            if (id != contrato.id())
                throw new ResponseStatusException(HttpStatus.FOUND, "Contract with Id: " + id + " overlaps.");
        });
        return contratosRep.save(contrato);
    }

    @DeleteMapping("/contratos/{id}")
    public void deleteContratos(@PathVariable("id") long id) {
        contratosRep.deleteContrato(id);
    }

    private Optional<Integer> getContractForRange(ContratoDeAlquiler contrato) {
        return contratosRep.existsContractForRange(
                contrato.idConsultorio(),
                contrato.idProfesional(),
                contrato.inicioDelContratoDeAlquiler(),
                contrato.finDelContrato()
        );
    }

}
