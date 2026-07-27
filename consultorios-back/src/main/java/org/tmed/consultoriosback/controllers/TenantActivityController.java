package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.TenantActivity;
import org.tmed.consultoriosback.repository.TenantActivityRepository;
import org.tmed.consultoriosback.services.TenantActivityService;

@RestController
@RequestMapping("/tenant-activities")
public class TenantActivityController {
    private final TenantActivityRepository tenantActivityRepository;
    private final TenantActivityService tenantActivityService;

    @Autowired
    public TenantActivityController(TenantActivityRepository tenantActivityRepository, TenantActivityService tenantActivityService) {
        this.tenantActivityRepository = tenantActivityRepository;
        this.tenantActivityService = tenantActivityService;
    }

    @GetMapping("/all")
    public Iterable<TenantActivity> getAll() {
        return tenantActivityRepository.findAll();
    }

    @GetMapping
    public Iterable<TenantActivity> getOpen() {
        return tenantActivityRepository.findOpen();
    }

    @GetMapping("/{id}")
    public TenantActivity getById(@PathVariable("id") long id) {
        return tenantActivityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping
    public TenantActivity create(@Validated @RequestBody TenantActivity tenantActivity) {
        return tenantActivityService.create(tenantActivity);
    }

    @PutMapping
    public TenantActivity update(@Validated @RequestBody TenantActivity tenantActivity) {
        if (tenantActivityRepository.existsById(tenantActivity.id())) return tenantActivityRepository.save(tenantActivity);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + tenantActivity.id() + " not found.");
    }

    @DeleteMapping("/{id}")
    public void close(@PathVariable("id") long id) {
        tenantActivityService.close(id);
    }
}
