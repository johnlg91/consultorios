package org.tmed.consultoriosback.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RecordatorioDePagoService {

    // Pending: determining who has an outstanding balance needs the billing engine
    // (REQUIREMENTS.md Phase 1 step 1.8, blocked on the proration open questions).
    // No-op until that lands; not currently scheduled anyway (@EnableScheduling is absent).
    @Scheduled(cron = "0 0 8 28 * ?")  // Primer día de cada mes
    public void enviarRecordatoriosDePago() {
    }
}
