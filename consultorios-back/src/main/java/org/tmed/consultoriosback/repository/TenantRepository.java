package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Tenant;

import java.util.Optional;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, Long> {

    @Query("SELECT * FROM tenants WHERE active = true")
    Iterable<Tenant> findActive();

    @Query("SELECT * FROM tenants WHERE national_id = :nationalId AND active = true")
    Optional<Tenant> findByNationalId(@Param("nationalId") long nationalId);

    @Modifying
    @Query("UPDATE tenants SET active = false WHERE id = :id")
    void deactivate(@Param("id") long id);
}
