package org.tmed.consultoriosback.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.tmed.consultoriosback.email.EmailDetails;
import org.tmed.consultoriosback.email.EmailServiceImpl;
import org.tmed.consultoriosback.model.DTO.ContratoSinPagar;
import org.tmed.consultoriosback.model.Profesional;
import org.tmed.consultoriosback.repository.ContratosDeAlquilerRepositorio;
import org.tmed.consultoriosback.repository.ProfesionalesRepositorio;

@Service
public class RecordatorioDePagoService {

    private final EmailServiceImpl emailService;
    private final ContratosDeAlquilerRepositorio contratoRepo;
    private final ProfesionalesRepositorio profesionalRepo;

    public RecordatorioDePagoService(EmailServiceImpl emailService, ContratosDeAlquilerRepositorio contratoRepo, ProfesionalesRepositorio profesionalRepo) {
        this.emailService = emailService;
        this.contratoRepo = contratoRepo;
        this.profesionalRepo = profesionalRepo;
    }

    @Scheduled(cron = "0 0 8 28 * ?")  // Primer día de cada mes
    public void enviarRecordatoriosDePago() {
        Iterable<ContratoSinPagar> contratosPendientes = contratoRepo.getContratosSinPagar();
        for (ContratoSinPagar contrato : contratosPendientes) {
            EmailDetails emailDetails = crearDetalleEmail(contrato);
            emailService.sendSimpleMail(emailDetails);
        }
    }

    private EmailDetails crearDetalleEmail(ContratoSinPagar contrato) {
        Profesional profesional = profesionalRepo.getProfesionales(contrato.idProfesional());
        String asunto = "Recordatorio de Pago Pendiente";
        String cuerpo = String.format(
                """
                        Estimado/a %s,

                        Le recordamos que tiene un pago pendiente para el contrato #%d. El costo mínimo a pagar es de %d. Por favor, realice su pago lo antes posible para evitar interrupciones en el servicio.

                        Saludos cordiales,
                        El equipo de Consultorios""",
                profesional.nombre() + " " + profesional.apellido(),
                contrato.id(),
                contrato.costoPorModulo());

        return new EmailDetails(profesional.eMail(), cuerpo, asunto, null);
    }
}