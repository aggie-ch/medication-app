package io.github.aggie.medicalapp.model.projection;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GroupReadModelTest {
    @Test
    @DisplayName("should create null deadline for group when no medication deadlines")
    void constructor_noDeadlines_createsNullDeadline() {
        // given
        var source = new MedicationGroup();
        source.setName("Duomox");
        source.setMedications(Set.of(new Medication("Floractin", null)));

        // when
        var result = new GroupReadModel(source);

        // then
        assertThat(result).hasFieldOrPropertyWithValue("deadline", null);
    }
}
