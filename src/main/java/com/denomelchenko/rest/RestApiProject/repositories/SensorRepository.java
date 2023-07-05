package com.denomelchenko.rest.RestApiProject.repositories;

import com.denomelchenko.rest.RestApiProject.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findByName(String name);
}
