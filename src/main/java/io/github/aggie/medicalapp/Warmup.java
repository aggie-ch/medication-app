package io.github.aggie.medicalapp;

import io.github.aggie.medicalapp.model.Medication;
import io.github.aggie.medicalapp.model.MedicationGroup;
import io.github.aggie.medicalapp.model.MedicationGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    public static final Logger logger = LoggerFactory.getLogger(Warmup.class);
    private final MedicationGroupRepository groupRepository;

    public Warmup(final MedicationGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application warmup after context refreshed");
        final String name = "ApplicationContextEvent";
        if (!groupRepository.existsByName(name)) {
            logger.info("No required group found! Adding it!");
            var group = new MedicationGroup();
            group.setName(name);
            group.setMedications(Set.of(
                    new Medication("ContextClosedEvent", null, group),
                    new Medication("ContextRefreshedEvent", null, group),
                    new Medication("ContextStoppedEvent", null, group),
                    new Medication("ContextStartedEvent", null, group)
            ));
            groupRepository.save(group);
        }
    }
}
