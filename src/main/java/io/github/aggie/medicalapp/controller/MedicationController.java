package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
class MedicationController {
    private static final Logger logger = LoggerFactory.getLogger(MedicationController.class);
    private final MedicationRepository repository;

    MedicationController(MedicationRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/medications", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Medication>> readAllMedications() {
        logger.warn("Exposing all the medications");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/medications")
    public ResponseEntity<List<Medication>> readAllMedications(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/medications/{id}")
    public ResponseEntity<Medication> readMedication(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/medications")
    ResponseEntity<Medication> createMedication(@RequestBody @Valid Medication toCreate) {
        var medication = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + medication.getId())).body(medication);
    }

    @PutMapping("/medications/{id}")
    ResponseEntity<?> updateMedication(@PathVariable int id, @RequestBody @Valid Medication toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(medication -> {
                    medication.updateFrom(toUpdate);
                    repository.save(medication);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/medications/{id}")
    public ResponseEntity<?> toggleMedication(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(m -> m.setDiscount(!m.isDiscount()));

        return ResponseEntity.noContent().build();
    }
}
