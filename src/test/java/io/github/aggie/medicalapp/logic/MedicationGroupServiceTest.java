package io.github.aggie.medicalapp.logic;

import io.github.aggie.medicalapp.model.MedicationGroup;
import io.github.aggie.medicalapp.model.MedicationGroupRepository;
import io.github.aggie.medicalapp.model.MedicationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MedicationGroupServiceTest {

    @Test
    @DisplayName("should throw when undone medications")
    void toggleGroup_undoneMedications_throwsIllegalStateException() {
        // given
        var mockRepository = mock(MedicationRepository.class);
        when(mockRepository.existsByDiscountIsFalseAndGroup_Id(anyInt())).thenReturn(true);
        // system under test
        var toTest = new MedicationGroupService(null, mockRepository);

        // when
        var exception = catchThrowable(() -> toTest.toogleGroup(0));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Group has discount");
    }

    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        // given
        var mockRepository = mock(MedicationRepository.class);
        when(mockRepository.existsByDiscountIsFalseAndGroup_Id(anyInt())).thenReturn(false);
        // and
        var medicationGroupRepository = mock(MedicationGroupRepository.class);
        when(medicationGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        // system under test
        var toTest = new MedicationGroupService(medicationGroupRepository, mockRepository);

        // when
        var exception = catchThrowable(() -> toTest.toogleGroup(0));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("MedicationGroup with given");
    }

    @Test
    @DisplayName("should toggle group")
    void toggleGroup_worksAsExpected() {
        // given
        var mockRepository = mock(MedicationRepository.class);
        when(mockRepository.existsByDiscountIsFalseAndGroup_Id(anyInt())).thenReturn(false);
        // and
        var group = new MedicationGroup();
        var beforeToggle = group.isDiscount();
        // and
        var medicationGroupRepository = mock(MedicationGroupRepository.class);
        when(medicationGroupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        // system under test
        var toTest = new MedicationGroupService(medicationGroupRepository, mockRepository);

        // when
        toTest.toogleGroup(0);

        // then
        assertThat(group.isDiscount()).isEqualTo(!beforeToggle);
    }
}
