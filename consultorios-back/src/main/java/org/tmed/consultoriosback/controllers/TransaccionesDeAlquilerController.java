package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.TransaccionDeAlquiler;
import org.tmed.consultoriosback.repository.TransaccionesDeAlquilerRepositorio;

import java.util.ArrayList;

@RestController
public class TransaccionesDeAlquilerController {
    final TransaccionesDeAlquilerRepositorio transaccionesRep;

    @Autowired
    public TransaccionesDeAlquilerController(TransaccionesDeAlquilerRepositorio transaccionesRep) {
        this.transaccionesRep = transaccionesRep;
    }

    @GetMapping("/pagos/all")
    public Iterable<TransaccionDeAlquiler> getAllTransacciones() {
        return transaccionesRep.findAll();
    }

    @GetMapping("/pagos")
    public Iterable<TransaccionDeAlquiler> getTransaccion() {
        return transaccionesRep.getTransaccionDeAlquiler();
    }

    @GetMapping(value = "/pagos/{id}")
    public TransaccionDeAlquiler getTransaccion(@PathVariable("id") long id) {
        return transaccionesRep.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping("/pagos")
    public TransaccionDeAlquiler postconsultorios(@Validated @RequestBody TransaccionDeAlquiler pagos) {
        return transaccionesRep.save(pagos);
    }

    @PostMapping("/pagos/varios")
    public Iterable<TransaccionDeAlquiler> postconsultorios(@Validated @RequestBody Iterable<TransaccionDeAlquiler> pagos) {
        ArrayList<TransaccionDeAlquiler> nuevosPagos = new ArrayList<>();
        for (TransaccionDeAlquiler t : pagos) nuevosPagos.add(transaccionesRep.save(t));
        return nuevosPagos;
    }

    @PutMapping(value = "/pagos")
    public TransaccionDeAlquiler putconsultorios(@Validated @RequestBody TransaccionDeAlquiler pagos) {
        if (transaccionesRep.existsById(pagos.getId())) return transaccionesRep.save(pagos);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + pagos.getId() + " not found.");
    }

//    @DeleteMapping("/consultorios/{id}")
//    public void deleteconsultorios(@PathVariable("id") long id) {
//        transaccionesRep.deleteConsultorio(id);
//    }
}
