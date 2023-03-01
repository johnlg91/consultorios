package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Expensa;
import org.tmed.consultoriosback.repository.ExpensasRepositorio;

@RestController
public class ExpensasController {
    final ExpensasRepositorio expensasRep;

    @Autowired
    public ExpensasController(ExpensasRepositorio expensasRep) {
        this.expensasRep = expensasRep;
    }

    @GetMapping("/expensas/all")
    public Iterable<Expensa> getAllExpensas() {
        return expensasRep.findAll();
    }

    @GetMapping("/expensas")
    public Iterable<Expensa> getExpensas() {
        return expensasRep.getExpensas();
    }

    @GetMapping(value = "/expensas/{id}")
    public Expensa getExpensas(@PathVariable("id") long id) {
        return expensasRep.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping("/expensas")
    public Expensa postExpensas(@Validated @RequestBody Expensa consultorio) {
        return expensasRep.save(consultorio);
    }

    @PutMapping(value = "/expensas")
    public Expensa putExpensas(@Validated @RequestBody Expensa consultorio) {
        if (expensasRep.existsById(consultorio.getId())) return expensasRep.save(consultorio);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + consultorio.getId() + " not found.");
    }

    @DeleteMapping("/expensas/{id}")
    public void deleteExpensas(@PathVariable("id") long id) {
        expensasRep.deleteExpensa(id);
    }
}
