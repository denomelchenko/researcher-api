package server.services;

import server.models.Measurement;
import server.repositories.MeasurementRepository;
import server.util.measurement.MeasurementNotFoundException;
import server.util.sensor.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return measurementRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
    }

    public int getAllRainingDaysCount() {
        return measurementRepository.findByRainingTrue().size();
    }

    @Transactional
    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
    }
}
