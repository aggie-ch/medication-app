package io.github.aggie.medicalapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("medication")
public class MedicationConfigurationProperties {
    private Template template;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public static class Template {
        private boolean allowMultipleMedications;

        public boolean isAllowMultipleMedications() {
            return allowMultipleMedications;
        }

        public void setAllowMultipleMedications(boolean allowMultipleMedications) {
            this.allowMultipleMedications = allowMultipleMedications;
        }
    }
}
