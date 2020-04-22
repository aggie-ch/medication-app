package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.logic.TemplateService;
import io.github.aggie.medicalapp.model.TemplateStep;
import io.github.aggie.medicalapp.model.projection.TemplateWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/templates")
class TemplateController {
    private final TemplateService service;

    TemplateController(final TemplateService service) {
        this.service = service;
    }

    @GetMapping
    String showTemplates(Model model) {
        model.addAttribute("template", new TemplateWriteModel());
        return "templates";
    }

    @PostMapping
    String addProject(@ModelAttribute("template") TemplateWriteModel current, Model model) {
        service.save(current);
        model.addAttribute("template", new TemplateWriteModel());
        model.addAttribute("message", "Zapisano zlecone leczenie");
        return "templates";
    }

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("template") TemplateWriteModel current) {
        current.getSteps().add(new TemplateStep());
        return "templates";
    }
}
