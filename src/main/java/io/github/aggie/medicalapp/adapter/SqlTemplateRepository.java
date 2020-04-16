package io.github.aggie.medicalapp.adapter;

import io.github.aggie.medicalapp.model.Template;
import io.github.aggie.medicalapp.model.TemplateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTemplateRepository extends TemplateRepository, JpaRepository<Template, Integer> {
    @Override
    @Query("select distinct t from Template t join fetch t.steps")
    List<Template> findAll();
}
