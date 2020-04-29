package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.logic.MedicationService;
import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/medications")
class MedicationController {
    private static final Logger logger = LoggerFactory.getLogger(MedicationController.class);
    private final ApplicationEventPublisher eventPublisher;
    private final MedicationRepository repository;
    private final MedicationService service;

    MedicationController(ApplicationEventPublisher eventPublisher, MedicationRepository repository, MedicationService service) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    public CompletableFuture<ResponseEntity<List<Medication>>> readAllMedications() {
        logger.warn("Exposing all the medications");
        return service.findAllAsync().thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public ResponseEntity<List<Medication>> readAllMedications(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> readMedication(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/discount")
    public ResponseEntity<List<Medication>> readDiscountMedication(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(repository.findByDiscount(state));
    }

    @PostMapping
    public ResponseEntity<Medication> createMedication(@RequestBody @Valid Medication toCreate) {
        var medication = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + medication.getId())).body(medication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedication(@PathVariable int id, @RequestBody @Valid Medication toUpdate) {
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
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleMedication(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .map(Medication::toggle)
                .ifPresent(eventPublisher::publishEvent);
        return ResponseEntity.noContent().build();
    }
}
