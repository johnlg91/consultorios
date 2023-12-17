package org.tmed.consultoriosback.email;

//import com.SpringBootEmail.Entity.EmailDetails;
//import com.SpringBootEmail.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tmed.consultoriosback.services.RecordatorioDePagoService;

@RestController
public class EmailController {

    private final EmailService emailService;
    private final RecordatorioDePagoService recordatorioDePagoService;

    @Autowired
    public EmailController(EmailService emailService, RecordatorioDePagoService recordatorioDePagoService) {
        this.emailService = emailService;
        this.recordatorioDePagoService = recordatorioDePagoService;
    }

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details) {
        return emailService.sendSimpleMail(details);
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        return emailService.sendMailWithAttachment(details);
    }

    @GetMapping("/enviarRecordatorios")
    public String testEnviarRecordatoriosDePago() {
        recordatorioDePagoService.enviarRecordatoriosDePago();
        return "Solicitud de envío de recordatorios iniciada.";
    }
}
