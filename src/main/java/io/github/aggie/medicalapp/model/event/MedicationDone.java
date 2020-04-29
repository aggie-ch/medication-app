package io.github.aggie.medicalapp.model.event;

import io.github.aggie.medicalapp.model.Medication;

import java.time.Clock;

public class MedicationDone extends MedicationEvent {
    MedicationDone(Medication source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
