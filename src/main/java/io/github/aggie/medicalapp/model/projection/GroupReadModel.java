package io.github.aggie.medicalapp.model.projection;

import io.github.aggie.medicalapp.model.MedicationGroup;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {
    private int id;
    private String name;
    private LocalDateTime deadline;
    private Set<GroupMedicationReadModel> medications;

    public GroupReadModel(MedicationGroup source) {
        id = source.getId();
        name = source.getName();
        source.getMedications().stream()
                .map(t -> t.getDeadline())
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> deadline = date);
        medications = source.getMedications().stream()
                .map(GroupMedicationReadModel::new)
                .collect(Collectors.toSet());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Set<GroupMedicationReadModel> getMedications() {
        return medications;
    }

    public void setMedications(Set<GroupMedicationReadModel> medications) {
        this.medications = medications;
    }
}
