package io.github.aggie.medicalapp.logic;

import io.github.aggie.medicalapp.MedicationConfigurationProperties;
import io.github.aggie.medicalapp.model.MedicationGroupRepository;
import io.github.aggie.medicalapp.model.Template;
import io.github.aggie.medicalapp.model.TemplateRepository;
import io.github.aggie.medicalapp.model.projection.GroupMedicationWriteModel;
import io.github.aggie.medicalapp.model.projection.GroupReadModel;
import io.github.aggie.medicalapp.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateService {
    private TemplateRepository repository;
    private MedicationGroupRepository medicationGroupRepository;
    private MedicationConfigurationProperties config;
    private MedicationGroupService medicationGroupService;

    public TemplateService(TemplateRepository repository, MedicationGroupRepository medicationGroupRepository, MedicationGroupService medicationGroupService, MedicationConfigurationProperties config) {
        this.repository = repository;
        this.medicationGroupRepository = medicationGroupRepository;
        this.medicationGroupService = medicationGroupService;
        this.config = config;
    }

    public List<Template> readAll() {
        return repository.findAll();
    }

    public Template createTemplate(final Template source) {
        return repository.save(source);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int templateId) {
        if (!config.getTemplate().isAllowMultipleMedications() &&
                medicationGroupRepository.existsByDiscountIsFalseAndTemplate_Id(templateId)) {
            throw new IllegalStateException("Only one undone group from template is allowed");
        }
        return repository.findById(templateId)
                .map(template -> {
                    var group = new GroupWriteModel();
                    group.setName(template.getDescription());
                    group.setMedications(
                            template.getSteps().stream()
                                    .map(templateStep -> {
                                                var medication = new GroupMedicationWriteModel();
                                                medication.setName(templateStep.getDescription());
                                                medication.setDeadline(deadline.plusDays(templateStep.getDaysToDeadline()));
                                                return medication;
                                            }
                                    ).collect(Collectors.toSet())
                    );
                    return medicationGroupService.createGroup(group);
                }).orElseThrow(() -> new IllegalArgumentException("Template with given id not found"));
    }
}
