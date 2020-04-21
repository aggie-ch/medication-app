package io.github.aggie.medicalapp.logic;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MedicationService {
    private static final Logger logger = LoggerFactory.getLogger(MedicationService.class);
    private final MedicationRepository repository;

    public MedicationService(MedicationRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<List<Medication>> findAllAsync() {
        logger.info("Supply findAllAsync method");
        return CompletableFuture.supplyAsync(repository::findAll);
    }
}
