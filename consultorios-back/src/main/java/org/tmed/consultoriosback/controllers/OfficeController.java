package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Office;
import org.tmed.consultoriosback.repository.OfficeRepository;

@RestController
@RequestMapping("/offices")
public class OfficeController {
    private final OfficeRepository officeRepository;

    @Autowired
    public OfficeController(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @GetMapping("/all")
    public Iterable<Office> getAll() {
        return officeRepository.findAll();
    }

    @GetMapping
    public Iterable<Office> getActive() {
        return officeRepository.findActive();
    }

    @GetMapping("/{id}")
    public Office getById(@PathVariable("id") long id) {
        return officeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping
    public Office create(@Validated @RequestBody Office office) {
        return officeRepository.save(office);
    }

    @PutMapping
    public Office update(@Validated @RequestBody Office office) {
        if (officeRepository.existsById(office.id())) return officeRepository.save(office);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + office.id() + " not found.");
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable("id") long id) {
        officeRepository.deactivate(id);
    }
}
