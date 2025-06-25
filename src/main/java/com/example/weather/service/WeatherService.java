package com.example.weather.service;

import com.example.weather.client.WeatherClient;
import com.example.weather.model.WeatherEntry;
import com.example.weather.model.WeatherResponse;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.annotation.Value;

@Singleton
public class WeatherService {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherClient weatherClient;

    private final WeatherEntryRepository repository;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.default-city}")
    private String defaultCity;

    @Value("${weather.api.units}")
    private String units;

    public WeatherService(WeatherClient weatherClient, WeatherEntryRepository repository) {
        this.weatherClient = weatherClient;
        this.repository = repository;
    }

    public WeatherResponse fetchWeather(String city) {
        LOG.info("Hämtar väder för {}", city);
        return weatherClient.getWeatherByCity(city, apiKey, units);
    }

    public WeatherResponse fetchDefaultWeather() {
        LOG.info("Hämtar väder för standardstad: {}", defaultCity);
        return weatherClient.getWeatherByCity(defaultCity, apiKey, units);
    }

    public WeatherEntry saveWeather(WeatherResponse response) {
        WeatherEntry entry = new WeatherEntry(
                response.getName(),
                response.getMain().getTemp(),
                response.getMain().getHumidity(),
                response.getWeather().get(0).getDescription(),
                response.getWind().getSpeed()
        );
        return repository.save(entry);
    }

    public boolean isExtremeWeather(WeatherResponse response) {
        double temp = response.getMain().getTemp();
        return temp >= 30 || temp <= -15;
    }
}
