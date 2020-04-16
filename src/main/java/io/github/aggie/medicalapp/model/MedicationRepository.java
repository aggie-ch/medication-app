package io.github.aggie.medicalapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository {
    List<Medication> findAll();

    Page<Medication> findAll(Pageable page);

    Optional<Medication> findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByDiscountIsFalseAndGroup_Id(Integer id);

    Medication save(Medication entity);

    List<Medication> findByDiscount(boolean discount);
}
