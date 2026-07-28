package org.tmed.consultoriosback.services;

import org.springframework.stereotype.Service;
import org.tmed.consultoriosback.model.Vacancy;
import org.tmed.consultoriosback.repository.VacancyRepository;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final ConflictValidationService conflictValidationService;

    public VacancyService(VacancyRepository vacancyRepository, ConflictValidationService conflictValidationService) {
        this.vacancyRepository = vacancyRepository;
        this.conflictValidationService = conflictValidationService;
    }

    public Vacancy save(Vacancy vacancy) {
        conflictValidationService.validateVacancy(vacancy);
        return vacancyRepository.save(vacancy);
    }
}
