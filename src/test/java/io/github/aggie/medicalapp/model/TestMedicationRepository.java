package io.github.aggie.medicalapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
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
        int key = medications.size() + 1;
        Field field = null;
        try {
            field = Medication.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, key);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        medications.put(key, entity);
        return medications.get(key);
    }

    @Override
    public List<Medication> findByDiscount(boolean discount) {
        return null;
    }
}
