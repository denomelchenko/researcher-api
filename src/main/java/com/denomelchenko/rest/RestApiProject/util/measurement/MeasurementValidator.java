package com.denomelchenko.rest.RestApiProject.util.measurement;

import com.denomelchenko.rest.RestApiProject.models.Measurement;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if (measurement.getSensor() == null) {
            errors.rejectValue("sensor", "", "Sensor is`t exist");
        }
    }
}
