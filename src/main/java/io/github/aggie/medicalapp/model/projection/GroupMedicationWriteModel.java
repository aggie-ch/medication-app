package io.github.aggie.medicalapp.model.projection;

import io.github.aggie.medicalapp.model.Medication;

import java.time.LocalDateTime;

public class GroupMedicationWriteModel {
    private String name;
    private LocalDateTime deadline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Medication toMedication() {
        return new Medication(name, deadline);
    }
}
