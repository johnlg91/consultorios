package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Tenant;
import org.tmed.consultoriosback.repository.TenantRepository;

@RestController
@RequestMapping("/tenants")
public class TenantController {
    private final TenantRepository tenantRepository;

    @Autowired
    public TenantController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/all")
    public Iterable<Tenant> getAll() {
        return tenantRepository.findAll();
    }

    @GetMapping
    public Iterable<Tenant> getActive() {
        return tenantRepository.findActive();
    }

    @GetMapping("/national-id/{nationalId}")
    public Tenant getByNationalId(@PathVariable("nationalId") long nationalId) {
        return tenantRepository.findByNationalId(nationalId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "National id: " + nationalId + " not found."
        ));
    }

    @GetMapping("/{id}")
    public Tenant getById(@PathVariable("id") long id) {
        return tenantRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping
    public Tenant create(@Validated @RequestBody Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @PutMapping
    public Tenant update(@Validated @RequestBody Tenant tenant) {
        if (tenantRepository.existsById(tenant.id())) return tenantRepository.save(tenant);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + tenant.id() + " not found.");
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable("id") long id) {
        tenantRepository.deactivate(id);
    }
}
