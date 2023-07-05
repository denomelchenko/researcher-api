package com.denomelchenko.rest.RestApiProject.repositories;

import com.denomelchenko.rest.RestApiProject.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findByRainingTrue();
}
