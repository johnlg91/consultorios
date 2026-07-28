package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Vacancy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {

    @Query("""
            SELECT v.id FROM vacancies v
            JOIN tenant_activities ta ON v.tenant_activity_id = ta.id
            WHERE ta.office_id = :officeId
              AND v.day_of_week = :dayOfWeek
              AND (ta.end_date IS NULL OR ta.end_date >= CURRENT_DATE)
              AND v.start_time < :endTime AND :startTime < v.end_time
              AND v.id <> :excludeId
            LIMIT 1
            """)
    Optional<Long> findConflictingVacancy(@Param("officeId") long officeId,
                                           @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                           @Param("startTime") LocalTime startTime,
                                           @Param("endTime") LocalTime endTime,
                                           @Param("excludeId") long excludeId);

    @Query("""
            SELECT v.id FROM vacancies v
            JOIN tenant_activities ta ON v.tenant_activity_id = ta.id
            WHERE ta.office_id = :officeId
              AND v.day_of_week = :dayOfWeek
              AND ta.start_date <= :date
              AND (ta.end_date IS NULL OR ta.end_date >= :date)
              AND v.start_time < :endTime AND :startTime < v.end_time
            LIMIT 1
            """)
    Optional<Long> findConflictingVacancyOnDate(@Param("officeId") long officeId,
                                                 @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                                 @Param("date") LocalDate date,
                                                 @Param("startTime") LocalTime startTime,
                                                 @Param("endTime") LocalTime endTime);
}
