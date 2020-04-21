package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.logic.MedicationGroupService;
import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import io.github.aggie.medicalapp.model.projection.GroupReadModel;
import io.github.aggie.medicalapp.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
class MedicationGroupController {
    private static final Logger logger = LoggerFactory.getLogger(MedicationGroupController.class);
    private final MedicationGroupService groupService;
    private final MedicationRepository medicationRepository;

    public MedicationGroupController(MedicationGroupService groupService, MedicationRepository medicationRepository) {
        this.groupService = groupService;
        this.medicationRepository = medicationRepository;
    }

    @PostMapping
    public ResponseEntity<GroupReadModel> createGroupMedication(@RequestBody @Valid GroupWriteModel group) {
        var result = groupService.createGroup(group);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping
    public ResponseEntity<List<GroupReadModel>> readAllGroupMedication() {
        return ResponseEntity.ok(groupService.readAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroupMedication(@PathVariable int id) {
        groupService.toogleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/medications")
    public ResponseEntity<List<Medication>> readAllMedicationsFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(medicationRepository.findByGroupId(id));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
