package com.example.weather.repository;

import com.example.weather.model.WeatherEntry;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface WeatherEntryRepository extends CrudRepository<WeatherEntry, Long> {

    // Sorterar alla poster på timestamp DESC
    Iterable<WeatherEntry> findAllOrderByTimestampDesc();
}
