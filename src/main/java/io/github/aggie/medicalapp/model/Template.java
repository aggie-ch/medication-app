package io.github.aggie.medicalapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Template's description must not be empty")
    private String description;

    @OneToMany(mappedBy = "template")
    private Set<MedicationGroup> medicationGroups;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "template")
    private Set<TemplateStep> steps;

    public Template() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MedicationGroup> getMedicationGroups() {
        return medicationGroups;
    }

    public void setMedicationGroups(Set<MedicationGroup> medicationGroups) {
        this.medicationGroups = medicationGroups;
    }

    public Set<TemplateStep> getSteps() {
        return steps;
    }

    public void setSteps(Set<TemplateStep> steps) {
        this.steps = steps;
    }
}
