package io.github.aggie.medicalapp.model.projection;

import io.github.aggie.medicalapp.model.MedicationGroup;
import io.github.aggie.medicalapp.model.Template;

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

    public MedicationGroup toGroup(Template template) {
        var result = new MedicationGroup();
        result.setName(name);
        result.setMedications(medications.stream()
                .map(source -> source.toMedication(result))
                .collect(Collectors.toSet())
        );
        result.setTemplate(template);
        return result;
    }
}
