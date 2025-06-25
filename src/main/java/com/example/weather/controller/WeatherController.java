package com.example.weather.controller;


import com.example.weather.model.WeatherEntry;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.scheduling.TaskExecutors;
import jakarta.inject.Inject;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/api/weather")
public class WeatherController {

    @Inject
    WeatherService weatherService;

    @Get
    public WeatherEntry getAndStoreWeather(@QueryValue(defaultValue = "") String city) {
        WeatherResponse response = city.isBlank()
                ? weatherService.fetchDefaultWeather()
                : weatherService.fetchWeather(city);

        return weatherService.saveWeather(response);
    }

    @Get("/history")
    public Iterable<WeatherEntry> getWeatherHistory() {
        return weatherService.getHistory();
    }
}
