package io.github.aggie.medicalapp.model.projection;

import io.github.aggie.medicalapp.model.MedicationGroup;
import io.github.aggie.medicalapp.model.Template;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupWriteModel {
    @NotBlank(message = "Medication group's name must not be empty")
    private String name;
    private List<GroupMedicationWriteModel> medications = new ArrayList<>();

    public GroupWriteModel() {
        medications.add(new GroupMedicationWriteModel());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupMedicationWriteModel> getMedications() {
        return medications;
    }

    public void setMedications(List<GroupMedicationWriteModel> medications) {
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
