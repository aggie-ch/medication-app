package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MedicationControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    MedicationRepository repository;

    @Test
    void httpGet_returnAllTasks() {
        // given
        int initialSize = repository.findAll().size();
        repository.save(new Medication("duomox", LocalDateTime.now()));
        repository.save(new Medication("floractin", LocalDateTime.now()));

        // when
        Medication[] result = restTemplate.getForObject("http://localhost:" + port + "/medications", Medication[].class);

        // then
        assertThat(result).hasSize(initialSize + 2);
    }
}
