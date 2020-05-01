package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class MedicationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicationRepository repository;

    @Test
    void httpGet_returnGivenMedication() throws Exception {
        // given
        int id = repository.save(new Medication("ibuprofen", LocalDateTime.now())).getId();

        // when
        /**  Keycloak & Spring Security FIXME
         mockMvc.perform(get("/medications/" + id))
         .andExpect(status().is2xxSuccessful());
         */
    }
}
