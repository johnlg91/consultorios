package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Consultorio;
import org.tmed.consultoriosback.repository.ConsultoriosRepositorio;

@RestController
public class ConsultorioController {
    final ConsultoriosRepositorio consultoriosRep;

    @Autowired
    public ConsultorioController(ConsultoriosRepositorio consultoriosRep) {
        this.consultoriosRep = consultoriosRep;
    }

    @GetMapping("/consultorios/all")
    public Iterable<Consultorio> getAllConsultorios() {
        return consultoriosRep.findAll();
    }

    @GetMapping("/consultorios")
    public Iterable<Consultorio> getConsultorios() {
        return consultoriosRep.getConsultorios();
    }

    @GetMapping(value = "/consultorios/{id}")
    public Consultorio getconsultorios(@PathVariable("id") long id) {
        return consultoriosRep.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping("/consultorios")
    public Consultorio postconsultorios(@Validated @RequestBody Consultorio consultorio) {
        return consultoriosRep.save(consultorio);
    }

    @PutMapping(value = "/consultorios")
    public Consultorio putconsultorios(@Validated @RequestBody Consultorio consultorio) {
        if (consultoriosRep.existsById(consultorio.getId())) return consultoriosRep.save(consultorio);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + consultorio.getId() + " not found.");
    }

    @DeleteMapping("/consultorios/{id}")
    public void deleteconsultorios(@PathVariable("id") long id) {
        consultoriosRep.deleteConsultorio(id);
    }
}
