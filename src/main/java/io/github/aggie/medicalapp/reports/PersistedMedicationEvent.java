package io.github.aggie.medicalapp.reports;

import io.github.aggie.medicalapp.model.event.MedicationEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "medication_events")
public class PersistedMedicationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int medicationId;
    String name;
    LocalDateTime occurrence;

    public PersistedMedicationEvent() {
    }

    public PersistedMedicationEvent(MedicationEvent source) {
        medicationId = source.getMedicationId();
        name = source.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }
}
