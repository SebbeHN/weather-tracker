package com.example.weather.controller;

import com.example.weather.model.WeatherEntry;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;
import com.example.weather.repository.WeatherEntryRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.scheduling.TaskExecutors;
import jakarta.inject.Inject;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/api/weather")
public class WeatherController {

    @Inject
    private WeatherService weatherService;

    @Inject
    private WeatherEntryRepository weatherEntryRepository;

    /** Hämtar väder för valfri stad (eller default) och sparar posten */
    @Get
    public WeatherEntry getAndStoreWeather(@QueryValue(defaultValue = "") String city) {
        WeatherResponse response = city.isBlank()
                ? weatherService.fetchDefaultWeather()
                : weatherService.fetchWeather(city);

        return weatherService.saveWeather(response);
    }

    /** 🆕 Returnerar hela väderhistoriken, sorterad nyast först */
    @Get("/history")
    public Iterable<WeatherEntry> getHistory() {
        return weatherEntryRepository.findAllOrderByTimestampDesc();
    }

    @Get("/test-alert")
    public HttpResponse<String> sendTestAlert() {
        weatherService.sendTestAlert(); // ny metod i WeatherService
        return HttpResponse.ok("✅ Testnotis skickad via SNS!");
    }

}
