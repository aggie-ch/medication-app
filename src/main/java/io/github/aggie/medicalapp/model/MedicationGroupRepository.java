package io.github.aggie.medicalapp.model;

import java.util.List;
import java.util.Optional;

public interface MedicationGroupRepository {
    List<MedicationGroup> findAll();

    Optional<MedicationGroup> findById(Integer id);

    MedicationGroup save(MedicationGroup entity);

    boolean existsByDiscountIsFalseAndTemplate_Id(Integer templateId);
}
