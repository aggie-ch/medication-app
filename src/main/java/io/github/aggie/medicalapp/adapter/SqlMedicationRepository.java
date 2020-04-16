package io.github.aggie.medicalapp.adapter;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface SqlMedicationRepository extends MedicationRepository, JpaRepository<Medication, Integer> {
    @Override
    @Query(nativeQuery = true, value = "SELECT count(*) > 0 FROM medications WHERE id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDiscountIsFalseAndGroup_Id(Integer groupId);
}
