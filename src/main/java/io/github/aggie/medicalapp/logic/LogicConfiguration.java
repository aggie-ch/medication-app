package io.github.aggie.medicalapp.logic;

import io.github.aggie.medicalapp.MedicationConfigurationProperties;
import io.github.aggie.medicalapp.model.MedicationGroupRepository;
import io.github.aggie.medicalapp.model.TemplateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {
    @Bean
    TemplateService service(TemplateRepository repository,
                            MedicationGroupRepository medicationGroupRepository,
                            MedicationConfigurationProperties config) {
        return new TemplateService(repository, medicationGroupRepository, config);
    }
}
