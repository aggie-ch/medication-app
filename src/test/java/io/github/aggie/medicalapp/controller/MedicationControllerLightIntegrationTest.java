package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(MedicationController.class)
class MedicationControllerLightIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationRepository repository;

    @Test
    void httpGet_returnGivenMedication() throws Exception {
        // given
        String name = "clemastine";
        when(repository.findById(anyInt()))
                .thenReturn(Optional.of(new Medication(name, LocalDateTime.now())));

        // when + then
        mockMvc.perform(get("/medications/123"))
                .andDo(print())
                .andExpect(content().string(containsString("\"discount\":false")));
    }
}
