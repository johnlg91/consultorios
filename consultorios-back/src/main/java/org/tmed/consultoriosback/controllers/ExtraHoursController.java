package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.ExtraHours;
import org.tmed.consultoriosback.repository.ExtraHoursRepository;
import org.tmed.consultoriosback.services.ExtraHoursService;

@RestController
@RequestMapping("/extra-hours")
public class ExtraHoursController {
    private final ExtraHoursRepository extraHoursRepository;
    private final ExtraHoursService extraHoursService;

    @Autowired
    public ExtraHoursController(ExtraHoursRepository extraHoursRepository, ExtraHoursService extraHoursService) {
        this.extraHoursRepository = extraHoursRepository;
        this.extraHoursService = extraHoursService;
    }

    @GetMapping("/all")
    public Iterable<ExtraHours> getAll() {
        return extraHoursRepository.findAll();
    }

    @GetMapping
    public Iterable<ExtraHours> getActive() {
        return extraHoursRepository.findActive();
    }

    @GetMapping("/{id}")
    public ExtraHours getById(@PathVariable("id") long id) {
        return extraHoursRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping
    public ExtraHours create(@Validated @RequestBody ExtraHours extraHours) {
        return extraHoursService.save(extraHours);
    }

    @PutMapping
    public ExtraHours update(@Validated @RequestBody ExtraHours extraHours) {
        if (!extraHoursRepository.existsById(extraHours.id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + extraHours.id() + " not found.");
        }
        return extraHoursService.save(extraHours);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable("id") long id) {
        extraHoursService.deactivate(id);
    }
}
