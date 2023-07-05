package server.services;

import server.models.Sensor;
import server.repositories.SensorRepository;
import server.util.measurement.MeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.util.sensor.SensorNotFoundException;

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
        return sensorRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name);
    }
}
