package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.TenantActivity;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TenantActivityRepository extends CrudRepository<TenantActivity, Long> {

    @Query("SELECT * FROM tenant_activities WHERE end_date IS NULL")
    Iterable<TenantActivity> findOpen();

    @Query("SELECT * FROM tenant_activities WHERE tenant_id = :tenantId AND end_date IS NULL")
    Iterable<TenantActivity> findOpenByTenant(@Param("tenantId") long tenantId);

    @Query("SELECT id FROM tenant_activities WHERE tenant_id = :tenantId AND office_id = :officeId AND end_date IS NULL LIMIT 1")
    Optional<Long> findOpenForTenantAndOffice(@Param("tenantId") long tenantId, @Param("officeId") long officeId);

    @Modifying
    @Query("UPDATE tenant_activities SET end_date = :endDate WHERE id = :id")
    void close(@Param("id") long id, @Param("endDate") LocalDate endDate);
}
