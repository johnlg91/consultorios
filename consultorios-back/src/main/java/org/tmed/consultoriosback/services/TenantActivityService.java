package org.tmed.consultoriosback.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.TenantActivity;
import org.tmed.consultoriosback.repository.TenantActivityRepository;

import java.time.LocalDate;

@Service
public class TenantActivityService {

    private final TenantActivityRepository tenantActivityRepository;

    public TenantActivityService(TenantActivityRepository tenantActivityRepository) {
        this.tenantActivityRepository = tenantActivityRepository;
    }

    public TenantActivity create(TenantActivity tenantActivity) {
        tenantActivityRepository.findOpenForTenantAndOffice(tenantActivity.tenantId(), tenantActivity.officeId())
                .ifPresent(existingId -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Tenant already has an open activity at this office (id: " + existingId + ").");
                });
        return tenantActivityRepository.save(tenantActivity);
    }

    public void close(long id) {
        tenantActivityRepository.close(id, LocalDate.now());
    }
}
