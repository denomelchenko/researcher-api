package com.denomelchenko.rest.RestApiProject.dto;

import com.denomelchenko.rest.RestApiProject.models.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class MeasurementDTO {
    @Min(value = -150, message = "value should be greater than -150")
    @Max(value = 150, message = "value should be less than 150")
    private double value;

    private boolean raining;

    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
