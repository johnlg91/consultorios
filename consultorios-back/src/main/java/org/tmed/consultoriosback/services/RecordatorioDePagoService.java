package org.tmed.consultoriosback.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.tmed.consultoriosback.email.EmailDetails;
import org.tmed.consultoriosback.email.EmailServiceImpl;
import org.tmed.consultoriosback.model.DTO.ContratoSinPagar;
import org.tmed.consultoriosback.model.Tenant;
import org.tmed.consultoriosback.repository.ContratosDeAlquilerRepositorio;
import org.tmed.consultoriosback.repository.TenantRepository;

@Service
public class RecordatorioDePagoService {

    private final EmailServiceImpl emailService;
    private final ContratosDeAlquilerRepositorio contratoRepo;
    private final TenantRepository tenantRepository;

    public RecordatorioDePagoService(EmailServiceImpl emailService, ContratosDeAlquilerRepositorio contratoRepo, TenantRepository tenantRepository) {
        this.emailService = emailService;
        this.contratoRepo = contratoRepo;
        this.tenantRepository = tenantRepository;
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
        Tenant tenant = tenantRepository.findById(contrato.idProfesional())
                .orElseThrow(() -> new IllegalStateException("Tenant " + contrato.idProfesional() + " not found"));
        String asunto = "Recordatorio de Pago Pendiente";
        String cuerpo = String.format(
                """
                        Estimado/a %s,

                        Le recordamos que tiene un pago pendiente para el contrato #%d. El costo mínimo a pagar es de %d. Por favor, realice su pago lo antes posible para evitar interrupciones en el servicio.

                        Saludos cordiales,
                        El equipo de Consultorios""",
                tenant.firstName() + " " + tenant.lastName(),
                contrato.id(),
                contrato.costoPorModulo());

        return new EmailDetails(tenant.email(), cuerpo, asunto, null);
    }
}