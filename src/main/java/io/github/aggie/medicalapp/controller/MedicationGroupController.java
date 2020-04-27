package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.logic.MedicationGroupService;
import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import io.github.aggie.medicalapp.model.projection.GroupMedicationWriteModel;
import io.github.aggie.medicalapp.model.projection.GroupReadModel;
import io.github.aggie.medicalapp.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/groups")
class MedicationGroupController {
    private static final Logger logger = LoggerFactory.getLogger(MedicationGroupController.class);
    private final MedicationGroupService groupService;
    private final MedicationRepository medicationRepository;

    public MedicationGroupController(MedicationGroupService groupService, MedicationRepository medicationRepository) {
        this.groupService = groupService;
        this.medicationRepository = medicationRepository;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model) {
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel current,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "groups";
        }
        groupService.createGroup(current);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Zapisano grupę");
        return "groups";
    }

    @PostMapping(params = "addMedication", produces = MediaType.TEXT_HTML_VALUE)
    public String addGroupMedication(@ModelAttribute("group") GroupWriteModel current) {
        current.getMedications().add(new GroupMedicationWriteModel());
        return "groups";
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupReadModel> createGroupMedication(@RequestBody @Valid GroupWriteModel group) {
        var result = groupService.createGroup(group);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroupReadModel>> readAllGroupMedication() {
        return ResponseEntity.ok(groupService.readAll());
    }

    @ResponseBody
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroupMedication(@PathVariable int id) {
        groupService.toogleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @GetMapping(value = "/{id}/medications", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @ModelAttribute("groups")
    List<GroupReadModel> getGroups() {
        return groupService.readAll();
    }
}
