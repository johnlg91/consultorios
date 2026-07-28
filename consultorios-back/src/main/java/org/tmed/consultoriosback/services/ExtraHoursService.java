package org.tmed.consultoriosback.services;

import org.springframework.stereotype.Service;
import org.tmed.consultoriosback.model.ExtraHours;
import org.tmed.consultoriosback.repository.ExtraHoursRepository;

@Service
public class ExtraHoursService {

    private final ExtraHoursRepository extraHoursRepository;
    private final ConflictValidationService conflictValidationService;

    public ExtraHoursService(ExtraHoursRepository extraHoursRepository, ConflictValidationService conflictValidationService) {
        this.extraHoursRepository = extraHoursRepository;
        this.conflictValidationService = conflictValidationService;
    }

    public ExtraHours save(ExtraHours extraHours) {
        conflictValidationService.validateExtraHours(extraHours);
        return extraHoursRepository.save(extraHours);
    }

    public void deactivate(long id) {
        extraHoursRepository.deactivate(id);
    }
}
