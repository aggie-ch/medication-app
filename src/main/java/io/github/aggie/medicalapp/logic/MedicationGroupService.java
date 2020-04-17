package io.github.aggie.medicalapp.logic;

import io.github.aggie.medicalapp.model.MedicationGroupRepository;
import io.github.aggie.medicalapp.model.MedicationRepository;
import io.github.aggie.medicalapp.model.projection.GroupReadModel;
import io.github.aggie.medicalapp.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

public class MedicationGroupService {

    private MedicationGroupRepository repository;
    private MedicationRepository medicationRepository;

    public MedicationGroupService(final MedicationGroupRepository repository, MedicationRepository medicationRepository) {
        this.repository = repository;
        this.medicationRepository = medicationRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        var result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toogleGroup(int groupId) {
        if (medicationRepository.existsByDiscountIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has discount medication. Check it");
        }
        var result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("MedicationGroup with given id not found."));
        result.setDiscount(!result.isDiscount());
        repository.save(result);
    }
}
