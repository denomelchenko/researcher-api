package com.denomelchenko.rest.RestApiProject.controllers;

import com.denomelchenko.rest.RestApiProject.models.Sensor;
import com.denomelchenko.rest.RestApiProject.services.SensorService;
import com.denomelchenko.rest.RestApiProject.util.sensor.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid Sensor sensor, BindingResult bindingResult) {
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
