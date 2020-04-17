package io.github.aggie.medicalapp.adapter;

import io.github.aggie.medicalapp.model.MedicationGroup;
import io.github.aggie.medicalapp.model.MedicationGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlMedicationGroupRepository extends MedicationGroupRepository, JpaRepository<MedicationGroup, Integer> {
    @Override
    @Query(nativeQuery = true, value = "SELECT count(*) > 0 FROM medications WHERE id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    @Query("from MedicationGroup m join fetch m.medications")
    List<MedicationGroup> findAll();

    @Override
    boolean existsByDiscountIsFalseAndTemplate_Id(Integer templateId);
}
