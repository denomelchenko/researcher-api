package server.controllers;

import server.dto.MeasurementDTO;
import server.models.Measurement;
import server.services.MeasurementService;
import server.services.SensorService;
import server.util.measurement.MeasurementErrorResponse;
import server.util.measurement.MeasurementNotCreatedException;
import server.util.measurement.MeasurementNotFoundException;
import server.util.measurement.MeasurementValidator;
import server.util.sensor.SensorNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, SensorService sensorService, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping
    public List<MeasurementDTO> getAll() {
        return measurementService.getAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementService.getAllRainingDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);
        enrichMeasurement(measurement);
        measurementValidator.validate(measurement, bindingResult);
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMsg = new StringBuilder();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }
        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = new Measurement();
        measurement.setSensor(sensorService.findByName(measurementDTO.getSensor().getName()));
        measurement.setRaining(measurementDTO.isRaining());
        measurement.setValue(measurementDTO.getValue());
        return measurement;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(MeasurementNotCreatedException e) {
        return new ResponseEntity<>(new MeasurementErrorResponse(e.getMessage(), System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(MeasurementNotFoundException e) {
        return new ResponseEntity<>(new MeasurementErrorResponse("Invalid measure", System.currentTimeMillis()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(SensorNotFoundException e) {
        return new ResponseEntity<>(new MeasurementErrorResponse("Invalid sensor", System.currentTimeMillis()),
                HttpStatus.NOT_FOUND);
    }
}
