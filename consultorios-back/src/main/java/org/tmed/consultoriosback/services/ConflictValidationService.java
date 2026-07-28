package org.tmed.consultoriosback.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.tmed.consultoriosback.model.ExtraHours;
import org.tmed.consultoriosback.model.TenantActivity;
import org.tmed.consultoriosback.model.Vacancy;
import org.tmed.consultoriosback.repository.ExtraHoursRepository;
import org.tmed.consultoriosback.repository.TenantActivityRepository;
import org.tmed.consultoriosback.repository.VacancyRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ConflictValidationService {

    private static final LocalDate FAR_FUTURE = LocalDate.of(9999, 12, 31);

    private final TenantActivityRepository tenantActivityRepository;
    private final VacancyRepository vacancyRepository;
    private final ExtraHoursRepository extraHoursRepository;

    public ConflictValidationService(TenantActivityRepository tenantActivityRepository,
                                      VacancyRepository vacancyRepository,
                                      ExtraHoursRepository extraHoursRepository) {
        this.tenantActivityRepository = tenantActivityRepository;
        this.vacancyRepository = vacancyRepository;
        this.extraHoursRepository = extraHoursRepository;
    }

    public void validateVacancy(Vacancy candidate) {
        TenantActivity activity = getActivity(candidate.tenantActivityId());
        long officeId = activity.officeId();

        vacancyRepository.findConflictingVacancy(officeId, candidate.dayOfWeek(), candidate.startTime(), candidate.endTime(), candidate.id())
                .ifPresent(conflictId -> {
                    throw conflict("recurring slot", conflictId);
                });

        LocalDate toDate = activity.endDate() != null ? activity.endDate() : FAR_FUTURE;
        for (ExtraHours extraHours : extraHoursRepository.findActiveInRange(officeId, activity.startDate(), toDate)) {
            if (extraHours.date().getDayOfWeek() == candidate.dayOfWeek()
                    && overlaps(candidate.startTime(), candidate.endTime(), extraHours.startTime(), extraHours.endTime())) {
                throw conflict("extra-hours booking", extraHours.id());
            }
        }
    }

    public void validateExtraHours(ExtraHours candidate) {
        TenantActivity activity = getActivity(candidate.tenantActivityId());
        long officeId = activity.officeId();
        DayOfWeek dayOfWeek = candidate.date().getDayOfWeek();

        vacancyRepository.findConflictingVacancyOnDate(officeId, dayOfWeek, candidate.date(), candidate.startTime(), candidate.endTime())
                .ifPresent(conflictId -> {
                    throw conflict("recurring slot", conflictId);
                });

        extraHoursRepository.findConflictingExtraHours(officeId, candidate.date(), candidate.startTime(), candidate.endTime(), candidate.id())
                .ifPresent(conflictId -> {
                    throw conflict("extra-hours booking", conflictId);
                });
    }

    private TenantActivity getActivity(long tenantActivityId) {
        return tenantActivityRepository.findById(tenantActivityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tenant activity " + tenantActivityId + " not found."));
    }

    private static boolean overlaps(LocalTime startA, LocalTime endA, LocalTime startB, LocalTime endB) {
        return startA.isBefore(endB) && startB.isBefore(endA);
    }

    private static ResponseStatusException conflict(String kind, long conflictingId) {
        return new ResponseStatusException(HttpStatus.CONFLICT,
                "Office already booked (conflicts with " + kind + " id: " + conflictingId + ").");
    }
}
