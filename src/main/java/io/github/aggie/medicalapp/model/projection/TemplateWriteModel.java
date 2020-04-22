package io.github.aggie.medicalapp.model.projection;

import io.github.aggie.medicalapp.model.Template;
import io.github.aggie.medicalapp.model.TemplateStep;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TemplateWriteModel {
    @NotBlank(message = "Template's description must not be empty")
    private String description;
    @Valid
    private List<TemplateStep> steps = new ArrayList<>();

    public TemplateWriteModel() {
        steps.add(new TemplateStep());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TemplateStep> getSteps() {
        return steps;
    }

    public void setSteps(List<TemplateStep> steps) {
        this.steps = steps;
    }

    public Template toTemplate() {
        var result = new Template();
        result.setDescription(description);
        steps.forEach(step -> step.setTemplate(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}
