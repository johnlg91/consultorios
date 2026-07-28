package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Vacancy;
import org.tmed.consultoriosback.repository.VacancyRepository;
import org.tmed.consultoriosback.services.VacancyService;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyRepository vacancyRepository;
    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyRepository vacancyRepository, VacancyService vacancyService) {
        this.vacancyRepository = vacancyRepository;
        this.vacancyService = vacancyService;
    }

    @GetMapping
    public Iterable<Vacancy> getAll() {
        return vacancyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Vacancy getById(@PathVariable("id") long id) {
        return vacancyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping
    public Vacancy create(@Validated @RequestBody Vacancy vacancy) {
        return vacancyService.save(vacancy);
    }

    @PutMapping
    public Vacancy update(@Validated @RequestBody Vacancy vacancy) {
        if (!vacancyRepository.existsById(vacancy.id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + vacancy.id() + " not found.");
        }
        return vacancyService.save(vacancy);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        vacancyRepository.deleteById(id);
    }
}
