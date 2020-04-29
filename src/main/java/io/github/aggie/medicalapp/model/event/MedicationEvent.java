package io.github.aggie.medicalapp.model.event;

import io.github.aggie.medicalapp.model.Medication;

import java.time.Clock;
import java.time.Instant;

public abstract class MedicationEvent {
    public static MedicationEvent changed(Medication source) {
        return source.isDiscount() ? new MedicationDone(source) : new MedicationUndone(source);
    }

    private int medicationId;
    private Instant occurrence;

    MedicationEvent(int medicationId, Clock clock) {
        this.medicationId = medicationId;
        this.occurrence = Instant.now(clock);
    }

    public int getMedicationId() {
        return medicationId;
    }

    public Instant getOccurrence() {
        return occurrence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "medicationId=" + medicationId +
                ", occurrence=" + occurrence +
                '}';
    }
}
