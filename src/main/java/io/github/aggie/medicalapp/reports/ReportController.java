package io.github.aggie.medicalapp.reports;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
class ReportController {
    private final MedicationRepository medicationRepository;
    private final PersistedMedicationEventRepository eventRepository;

    ReportController(MedicationRepository medicationRepository, PersistedMedicationEventRepository eventRepository) {
        this.medicationRepository = medicationRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<MedicationWithChangesCount> readMedicationWithCount(@PathVariable int id) {
        return medicationRepository.findById(id)
                .map(medication -> new MedicationWithChangesCount(medication, eventRepository.findByMedicationId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private static class MedicationWithChangesCount {
        public String name;
        public boolean discount;
        public int changesCount;

        MedicationWithChangesCount(Medication medication, List<PersistedMedicationEvent> events) {
            name = medication.getName();
            discount = medication.isDiscount();
            changesCount = events.size();
        }
    }
}
