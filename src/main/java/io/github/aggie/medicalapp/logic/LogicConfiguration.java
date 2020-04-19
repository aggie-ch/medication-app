package io.github.aggie.medicalapp.logic;

import io.github.aggie.medicalapp.MedicationConfigurationProperties;
import io.github.aggie.medicalapp.model.MedicationGroupRepository;
import io.github.aggie.medicalapp.model.MedicationRepository;
import io.github.aggie.medicalapp.model.TemplateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {
    @Bean
    TemplateService templateService(final TemplateRepository repository,
                                    final MedicationGroupRepository medicationGroupRepository,
                                    final MedicationGroupService medicationGroupService,
                                    final MedicationConfigurationProperties config) {
        return new TemplateService(repository, medicationGroupRepository, medicationGroupService, config);
    }

    @Bean
    MedicationGroupService medicationGroupService(final MedicationGroupRepository repository,
                                                  final MedicationRepository medicationRepository) {
        return new MedicationGroupService(repository, medicationRepository);
    }
}
