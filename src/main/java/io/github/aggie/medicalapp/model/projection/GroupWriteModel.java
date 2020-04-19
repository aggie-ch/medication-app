package io.github.aggie.medicalapp.model.projection;

import io.github.aggie.medicalapp.model.MedicationGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {
    private String name;
    private Set<GroupMedicationWriteModel> medications;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GroupMedicationWriteModel> getMedications() {
        return medications;
    }

    public void setMedications(Set<GroupMedicationWriteModel> medications) {
        this.medications = medications;
    }

    public MedicationGroup toGroup() {
        var result = new MedicationGroup();
        result.setName(name);
        result.setMedications(medications.stream()
                .map(source -> source.toMedication(result))
                .collect(Collectors.toSet())
        );
        return result;
    }
}
