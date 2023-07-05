package server.controllers;

import org.modelmapper.ModelMapper;
import server.dto.MeasurementDTO;
import server.dto.SensorDTO;
import server.models.Measurement;
import server.models.Sensor;
import server.services.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import server.util.sensor.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<SensorDTO> getAll() {
        return sensorService.getAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDTO getOne(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.getOne(id));
    }

    @GetMapping("/{id}/measurements")
    public List<MeasurementDTO> getMeasurements(@PathVariable("id") int id) {
        return sensorService.getOne(id).getMeasurements().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> exceptionHandler(SensorNotCreatedException e) {
        return new ResponseEntity<>(new SensorErrorResponse(e.getMessage(), System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> exceptionHandler(SensorNotFoundException e) {
        return new ResponseEntity<>(new SensorErrorResponse("Invalid sensor", System.currentTimeMillis()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> exceptionHandler(SensorAlreadyExistException e) {
        return new ResponseEntity<>(new SensorErrorResponse("Sensor already exist", System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST);
    }
}
