package io.github.aggie.medicalapp.logic;

import io.github.aggie.medicalapp.MedicationConfigurationProperties;
import io.github.aggie.medicalapp.model.*;
import io.github.aggie.medicalapp.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TemplateServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwsIllegalStateException() {
        // given
        var mockGroupRepository = groupRepositoryReturning(true);
        // and
        var mockConfig = configurationReturning(false);
        // system under test
        var toTest = new TemplateService(null, mockGroupRepository, null, mockConfig);

        // when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configuration ok and no project for a given id")
    void createGroup_configurationOk_And_noProjects_throwsIllegalArgumentException() {
        // given
        var mockRepository = mock(TemplateRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        var mockConfig = configurationReturning(true);
        // system under test
        var toTest = new TemplateService(mockRepository, null, null, mockConfig);

        // when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test // happy path
    @DisplayName("should create a new group from project")
    void createGroup_configurationOk_existingProject_createsAndSavesGroup() {
        // given
        var today = LocalDate.now().atStartOfDay();
        // and
        var template = templateWith("example", Set.of(-1, -2));
        var mockRepository = mock(TemplateRepository.class);
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(template));
        // and
        InMemoryGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        var serviceWithInMemRepo = dummyGroupService(inMemoryGroupRepo);
        int countBeforeCall = inMemoryGroupRepo.count();
        // and
        var mockConfig = configurationReturning(true);
        // system under test
        var toTest = new TemplateService(mockRepository, inMemoryGroupRepo, serviceWithInMemRepo, mockConfig);

        // when
        GroupReadModel result = toTest.createGroup(today, 1);

        // then
        assertThat(result.getName()).isEqualTo("example");
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(result.getMedications()).allMatch(medication -> medication.getName().equals("desc"));
        assertThat(countBeforeCall + 1).isNotEqualTo(inMemoryGroupRepo.count());
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configured to allow just 1 group and no groups and no projects for a given id")
    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupExists_noProjects_throwsIllegalArgumentException() {
        // given
        var mockRepository = mock(TemplateRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        MedicationGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        // and
        var mockConfig = configurationReturning(true);
        // system under test
        var toTest = new TemplateService(mockRepository, mockGroupRepository, null, mockConfig);

        // when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    private MedicationGroupService dummyGroupService(final InMemoryGroupRepository inMemoryGroupRepo) {
        return new MedicationGroupService(inMemoryGroupRepo, null);
    }

    private Template templateWith(String templateDescription, Set<Integer> daysToDeadline) {
        Set<TemplateStep> steps = daysToDeadline.stream()
            .map(days -> {
                var step = mock(TemplateStep.class);
                when(step.getDescription()).thenReturn("desc");
                when(step.getDaysToDeadline()).thenReturn(days);
                return step;
            })
            .collect(Collectors.toSet());
        var result = mock(Template.class);
        when(result.getDescription()).thenReturn(templateDescription);
        when(result.getSteps()).thenReturn(steps);
        return result;
    }

    private MedicationGroupRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(MedicationGroupRepository.class);
        when(mockGroupRepository.existsByDiscountIsFalseAndTemplate_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private MedicationConfigurationProperties configurationReturning(final boolean result) {
        var mockTemplate = mock(MedicationConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleMedications()).thenReturn(result);
        var mockConfig = mock(MedicationConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    private InMemoryGroupRepository inMemoryGroupRepository() {
        return new InMemoryGroupRepository();
    }

    private static class InMemoryGroupRepository implements MedicationGroupRepository {
        private int index = 0;
        private Map<Integer, MedicationGroup> map = new HashMap<>();

        int count() {
            return map.values().size();
        }

        @Override
        public List<MedicationGroup> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<MedicationGroup> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public MedicationGroup save(final MedicationGroup entity) {
            if (entity.getId() == 0) {
                try {
                    var field = MedicationGroup.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, ++index);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                map.put(entity.getId(), entity);
            }
            return entity;
        }

        @Override
        public boolean existsByDiscountIsFalseAndTemplate_Id(Integer templateId) {
            return map.values().stream()
                    .filter(group -> !group.isDiscount())
                    .anyMatch(group -> group.getTemplate() != null
                            && group.getTemplate().getId() == templateId);
        }

        @Override
        public boolean existsByName(String name) {
            return map.values().stream()
                    .anyMatch(group -> group.getName().equals(name));
        }
    }
}
