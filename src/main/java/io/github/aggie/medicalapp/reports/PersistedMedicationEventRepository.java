package io.github.aggie.medicalapp.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface PersistedMedicationEventRepository extends JpaRepository<PersistedMedicationEvent, Integer> {
    List<PersistedMedicationEvent> findByMedicationId(int medicationId);
}
