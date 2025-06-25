package com.example.weather.service;

import com.example.weather.model.WeatherEntry;
import io.micronaut.data.annotation.Repository;     // ✔ RÄTT
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface WeatherEntryRepository extends CrudRepository<WeatherEntry, Long> {
    Iterable<WeatherEntry> findAllByOrderByTimestampDesc();
}
