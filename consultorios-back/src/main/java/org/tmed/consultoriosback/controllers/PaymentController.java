package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.Payment;
import org.tmed.consultoriosback.repository.PaymentRepository;

import java.io.IOException;
import java.io.UncheckedIOException;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/all")
    public Iterable<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @GetMapping
    public Iterable<Payment> getActive() {
        return paymentRepository.findActive();
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable("id") long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
    }

    @PostMapping
    public Payment create(@Validated @RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }

    @PutMapping
    public Payment update(@Validated @RequestBody Payment payment) {
        if (paymentRepository.existsById(payment.id())) return paymentRepository.save(payment);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id: " + payment.id() + " not found.");
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable("id") long id) {
        paymentRepository.deactivate(id);
    }

    @PostMapping("/{id}/evidence")
    public Payment uploadEvidence(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
        paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
        try {
            paymentRepository.updateEvidenceImage(id, file.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read uploaded evidence file", e);
        }
        return paymentRepository.findById(id).orElseThrow();
    }

    @GetMapping("/{id}/evidence")
    public ResponseEntity<byte[]> getEvidence(@PathVariable("id") long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id: " + id + " not found."
        ));
        if (payment.evidenceImage() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No evidence image for payment " + id + ".");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(payment.evidenceImage());
    }
}
