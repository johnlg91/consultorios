package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Office;

@Repository
public interface OfficeRepository extends CrudRepository<Office, Long> {

    @Query("SELECT * FROM offices WHERE active = true ORDER BY number")
    Iterable<Office> findActive();

    @Modifying
    @Query("UPDATE offices SET active = false WHERE id = :id")
    void deactivate(@Param("id") long id);
}
