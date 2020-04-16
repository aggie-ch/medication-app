package io.github.aggie.medicalapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Medication's name must not be empty")
    private String name;
    private boolean discount;
    private LocalDateTime deadline;
    @Embedded
    private Audit audit = new Audit();

    @ManyToOne
    @JoinColumn(name = "medication_group_id")
    private MedicationGroup group;

    public Medication() {
    }

    public Medication(String name, LocalDateTime deadline) {
        this.name = name;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    MedicationGroup getGroup() {
        return group;
    }

    void setGroup(MedicationGroup group) {
        this.group = group;
    }

    public void updateFrom(final Medication source) {
        name = source.name;
        discount = source.discount;
        deadline = source.deadline;
        group = source.group;
    }
}
