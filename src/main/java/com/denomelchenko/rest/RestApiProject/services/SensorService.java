package com.denomelchenko.rest.RestApiProject.services;

import com.denomelchenko.rest.RestApiProject.models.Sensor;
import com.denomelchenko.rest.RestApiProject.repositories.SensorRepository;
import com.denomelchenko.rest.RestApiProject.util.measurement.MeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    public Sensor getOne(int id) {
        return sensorRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name);
    }
}
