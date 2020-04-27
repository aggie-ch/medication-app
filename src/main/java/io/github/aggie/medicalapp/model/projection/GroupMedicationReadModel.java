package io.github.aggie.medicalapp.model.projection;

import io.github.aggie.medicalapp.model.Medication;

public class GroupMedicationReadModel {
    private String name;
    private boolean discount;

    public GroupMedicationReadModel(Medication source) {
        name = source.getName();
        discount = source.isDiscount();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
