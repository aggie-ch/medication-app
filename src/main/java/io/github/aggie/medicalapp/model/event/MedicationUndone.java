package io.github.aggie.medicalapp.model.event;

import io.github.aggie.medicalapp.model.Medication;

import java.time.Clock;

public class MedicationUndone extends MedicationEvent {
    MedicationUndone(Medication source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
