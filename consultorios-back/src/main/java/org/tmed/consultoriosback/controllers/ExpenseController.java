package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Expense;
import org.tmed.consultoriosback.repository.ExpenseRepository;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/all")
    public Iterable<Expense> getAll() {
        return expenseRepository.findAll();
    }

    @GetMapping
    public Iterable<Expense> getActive() {
        return expenseRepository.findActive();
    }

    @GetMapping("/{id}")
    public Expense getById(@PathVariable("id") long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping
    public Expense create(@Validated @RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    @PutMapping
    public Expense update(@Validated @RequestBody Expense expense) {
        if (expenseRepository.existsById(expense.id())) return expenseRepository.save(expense);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + expense.id() + " not found.");
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable("id") long id) {
        expenseRepository.deactivate(id);
    }
}
