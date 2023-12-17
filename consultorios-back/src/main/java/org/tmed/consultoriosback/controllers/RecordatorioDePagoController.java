package org.tmed.consultoriosback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tmed.consultoriosback.services.RecordatorioDePagoService;

@RestController
public class RecordatorioDePagoController {

    @Autowired
    private RecordatorioDePagoService recordatorioDePagoService;

    @PostMapping("/enviarRecordatoriosDePago")
    public ResponseEntity<String> enviarRecordatoriosDePagoManualmente() {
        recordatorioDePagoService.enviarRecordatoriosDePago();
        return ResponseEntity.ok("Recordatorios de pago enviados.");
    }
}
