package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Query("SELECT * FROM payments WHERE active = true")
    Iterable<Payment> findActive();

    @Query("SELECT * FROM payments WHERE tenant_activity_id = :tenantActivityId AND active = true")
    Iterable<Payment> findActiveByTenantActivity(@Param("tenantActivityId") long tenantActivityId);

    @Modifying
    @Query("UPDATE payments SET evidence_image = :image WHERE id = :id")
    void updateEvidenceImage(@Param("id") long id, @Param("image") byte[] image);

    @Modifying
    @Query("UPDATE payments SET active = false WHERE id = :id")
    void deactivate(@Param("id") long id);
}
