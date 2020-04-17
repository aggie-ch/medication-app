package io.github.aggie.medicalapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class TestMedicationRepository implements MedicationRepository {
    private Map<Integer, Medication> medications = new HashMap<>();

    @Override
    public List<Medication> findAll() {
        return new ArrayList<>(medications.values());
    }

    @Override
    public Page<Medication> findAll(Pageable page) {
        return null;
    }

    @Override
    public Optional<Medication> findById(Integer id) {
        return Optional.ofNullable(medications.get(id));
    }

    @Override
    public boolean existsById(Integer id) {
        return medications.containsKey(id);
    }

    @Override
    public boolean existsByDiscountIsFalseAndGroup_Id(Integer id) {
        return false;
    }

    @Override
    public Medication save(Medication entity) {
        return medications.put(medications.size() + 1, entity);
    }

    @Override
    public List<Medication> findByDiscount(boolean discount) {
        return null;
    }
}
