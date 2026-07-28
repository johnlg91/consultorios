package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.ExtraHours;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ExtraHoursRepository extends CrudRepository<ExtraHours, Long> {

    @Query("SELECT * FROM extra_hours WHERE active = true")
    Iterable<ExtraHours> findActive();

    @Query("""
            SELECT eh.id FROM extra_hours eh
            JOIN tenant_activities ta ON eh.tenant_activity_id = ta.id
            WHERE ta.office_id = :officeId
              AND eh.date = :date
              AND eh.active = true
              AND eh.start_time < :endTime AND :startTime < eh.end_time
              AND eh.id <> :excludeId
            LIMIT 1
            """)
    Optional<Long> findConflictingExtraHours(@Param("officeId") long officeId,
                                              @Param("date") LocalDate date,
                                              @Param("startTime") LocalTime startTime,
                                              @Param("endTime") LocalTime endTime,
                                              @Param("excludeId") long excludeId);

    @Query("""
            SELECT eh.* FROM extra_hours eh
            JOIN tenant_activities ta ON eh.tenant_activity_id = ta.id
            WHERE ta.office_id = :officeId
              AND eh.active = true
              AND eh.date BETWEEN :fromDate AND :toDate
            """)
    Iterable<ExtraHours> findActiveInRange(@Param("officeId") long officeId,
                                            @Param("fromDate") LocalDate fromDate,
                                            @Param("toDate") LocalDate toDate);

    @Modifying
    @Query("UPDATE extra_hours SET active = false WHERE id = :id")
    void deactivate(@Param("id") long id);
}
