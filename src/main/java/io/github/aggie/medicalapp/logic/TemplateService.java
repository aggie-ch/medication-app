package io.github.aggie.medicalapp.logic;

import io.github.aggie.medicalapp.MedicationConfigurationProperties;
import io.github.aggie.medicalapp.model.*;
import io.github.aggie.medicalapp.model.projection.GroupReadModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateService {
    private TemplateRepository repository;
    private MedicationGroupRepository medicationGroupRepository;
    private MedicationConfigurationProperties config;

    public TemplateService(TemplateRepository repository, MedicationGroupRepository medicationGroupRepository, MedicationConfigurationProperties config) {
        this.repository = repository;
        this.medicationGroupRepository = medicationGroupRepository;
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
        MedicationGroup result = repository.findById(templateId)
                .map(template -> {
                    var group = new MedicationGroup();
                    group.setName(template.getDescription());
                    group.setMedications(
                            template.getSteps().stream()
                                    .map(templateStep -> new Medication(
                                            templateStep.getDescription(),
                                            deadline.plusDays(templateStep.getDaysToDeadline()))
                                    ).collect(Collectors.toSet())
                    );
                    group.setTemplate(template);
                    return medicationGroupRepository.save(group);
                }).orElseThrow(() -> new IllegalArgumentException("Template with given id not found"));
        return new GroupReadModel(result);
    }
}
