package io.github.aggie.medicalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MedicalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalAppApplication.class, args);
    }

    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
