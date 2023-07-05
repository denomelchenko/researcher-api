package com.denomelchenko.rest.RestApiProject.services;

import com.denomelchenko.rest.RestApiProject.models.Measurement;
import com.denomelchenko.rest.RestApiProject.repositories.MeasurementRepository;
import com.denomelchenko.rest.RestApiProject.util.sensor.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    public Measurement getOne(int id) {
        return measurementRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
    }
}
