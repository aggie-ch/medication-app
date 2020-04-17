package io.github.aggie.medicalapp;

import io.github.aggie.medicalapp.model.MedicationRepository;
import io.github.aggie.medicalapp.model.TestMedicationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfiguration {
    @Bean
    @Primary
    MedicationRepository testRepository() {
        return new TestMedicationRepository();
    }
}
