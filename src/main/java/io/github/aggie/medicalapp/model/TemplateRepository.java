package io.github.aggie.medicalapp.model;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository {
    List<Template> findAll();

    Optional<Template> findById(Integer id);

    Template save(Template entity);
}
