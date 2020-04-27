package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.logic.TemplateService;
import io.github.aggie.medicalapp.model.Template;
import io.github.aggie.medicalapp.model.TemplateStep;
import io.github.aggie.medicalapp.model.projection.TemplateWriteModel;
import io.micrometer.core.annotation.Timed;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/templates")
class TemplateController {
    private final TemplateService service;

    public TemplateController(final TemplateService service) {
        this.service = service;
    }

    @GetMapping
    public String showTemplates(Model model) {
        model.addAttribute("template", new TemplateWriteModel());
        return "templates";
    }

    @PostMapping
    public String addTemplate(
            @ModelAttribute("template") @Valid TemplateWriteModel current,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "templates";
        }
        service.save(current);
        model.addAttribute("template", new TemplateWriteModel());
        model.addAttribute("templates", getTemplates());
        model.addAttribute("message", "Zapisano zlecone leczenie");
        return "templates";
    }

    @PostMapping(params = "addStep")
    public String addTemplateStep(@ModelAttribute("template") TemplateWriteModel current) {
        current.getSteps().add(new TemplateStep());
        return "templates";
    }

    @Timed(value = "template.create.group", histogram = true, percentiles = {0.5, 0.95, 0.99})
    @PostMapping("/{id}")
    public String createGroup(@ModelAttribute("template") TemplateWriteModel current,
                              Model model,
                              @PathVariable int id,
                              @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline) {
        try {
            service.createGroup(deadline, id);
            model.addAttribute("message", "Dodano grupę!");
        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("message", "Błąd podczas tworzenia grupy!");
        }
        return "templates";
    }

    @ModelAttribute("templates")
    public List<Template> getTemplates() {
        return service.readAll();
    }
}
