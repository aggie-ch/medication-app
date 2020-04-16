package io.github.aggie.medicalapp.controller;

import io.github.aggie.medicalapp.MedicationConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    private DataSourceProperties datasource;
    private MedicationConfigurationProperties myProp;

    public InfoController(DataSourceProperties datasource, MedicationConfigurationProperties myProp) {
        this.datasource = datasource;
        this.myProp = myProp;
    }

    @GetMapping("info/url")
    String url() {
        return datasource.getUrl();
    }

    @GetMapping("info/prop")
    boolean myProp() {
        return myProp.getTemplate().isAllowMultipleMedications();
    }
}
