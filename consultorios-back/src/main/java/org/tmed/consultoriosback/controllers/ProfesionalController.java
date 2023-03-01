package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Profesional;
import org.tmed.consultoriosback.repository.ProfesionalesRepositorio;

@RestController
public class ProfesionalController {
    final ProfesionalesRepositorio profesionales;

    @Autowired
    public ProfesionalController(ProfesionalesRepositorio profesionales) {
        this.profesionales = profesionales;
    }

    @GetMapping("/profesionales/all")
    public Iterable<Profesional> getAllProfesionales() {
        return profesionales.findAll();
    }

    @GetMapping("/profesionales")
    public Iterable<Profesional> getProfesionales() {
        return profesionales.getProfesionales();
    }

    @GetMapping(value = "/profesionales/dni/{dni}")
    public Profesional getByDni(@PathVariable("dni") long dni) {
        return profesionales.findByDni(dni).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "DNI: " + dni + " not found."
        ));
    }

    @GetMapping(value = "/profesionales/{id}")
    public Profesional getProfesionales(@PathVariable("id") long id) {
        return profesionales.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping("/profesionales")
    public Profesional postProfesionales(@Validated @RequestBody Profesional profesional) {
        return profesionales.save(profesional);
    }

    @PutMapping(value = "/profesionales")
    public Profesional putProfesionales(@Validated @RequestBody Profesional profesional) {
        if (profesionales.existsById(profesional.getId())) return profesionales.save(profesional);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + profesional.getId() + " not found.");
    }

    @DeleteMapping("/profesionales/{id}")
    public void deleteProfesionales(@PathVariable("id") long id) {
        profesionales.deleteProfesional(id);
    }

}
