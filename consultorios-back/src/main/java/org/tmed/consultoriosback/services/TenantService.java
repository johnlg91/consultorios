package org.tmed.consultoriosback.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmed.consultoriosback.model.TenantActivity;
import org.tmed.consultoriosback.repository.TenantActivityRepository;
import org.tmed.consultoriosback.repository.TenantRepository;

import java.time.LocalDate;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantActivityRepository tenantActivityRepository;

    public TenantService(TenantRepository tenantRepository, TenantActivityRepository tenantActivityRepository) {
        this.tenantRepository = tenantRepository;
        this.tenantActivityRepository = tenantActivityRepository;
    }

    @Transactional
    public void deactivate(long tenantId) {
        tenantRepository.deactivate(tenantId);
        LocalDate today = LocalDate.now();
        for (TenantActivity activity : tenantActivityRepository.findOpenByTenant(tenantId)) {
            tenantActivityRepository.close(activity.id(), today);
        }
    }
}
