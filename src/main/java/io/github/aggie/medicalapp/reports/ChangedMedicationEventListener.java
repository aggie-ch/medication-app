package io.github.aggie.medicalapp.reports;

import io.github.aggie.medicalapp.model.event.MedicationDone;
import io.github.aggie.medicalapp.model.event.MedicationEvent;
import io.github.aggie.medicalapp.model.event.MedicationUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class ChangedMedicationEventListener {
    private static final Logger logger = LoggerFactory.getLogger(ChangedMedicationEventListener.class);
    private final PersistedMedicationEventRepository repository;

    ChangedMedicationEventListener(PersistedMedicationEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void on(MedicationDone event) {
        onChanged(event);
    }

    @Async
    @EventListener
    public void on(MedicationUndone event) {
        onChanged(event);
    }

    private void onChanged(MedicationEvent event) {
        logger.info("Got " + event);
        repository.save(new PersistedMedicationEvent(event));
    }
}
