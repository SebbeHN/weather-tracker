package com.example.weather.service;

import com.example.weather.client.WeatherClient;
import com.example.weather.model.WeatherEntry;
import com.example.weather.model.WeatherResponse;
import com.example.weather.repository.WeatherEntryRepository;
import jakarta.inject.Singleton;
import jakarta.inject.Inject;

import io.micronaut.context.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class WeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherClient weatherClient;
    private final WeatherEntryRepository repository;
    private final WeatherAlertService weatherAlertService;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.default-city}")
    private String defaultCity;

    @Value("${weather.api.units}")
    private String units;

    @Inject
    public WeatherService(WeatherClient weatherClient,
                          WeatherEntryRepository repository,
                          WeatherAlertService weatherAlertService) {
        this.weatherClient = weatherClient;
        this.repository = repository;
        this.weatherAlertService = weatherAlertService;
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
        double temp = response.getMain().getTemp();
        WeatherEntry entry = new WeatherEntry(
                response.getName(),
                temp,
                response.getMain().getHumidity(),
                response.getWeather().get(0).getDescription(),
                response.getWind().getSpeed()
        );

        WeatherEntry saved = repository.save(entry);

        if (isExtremeWeather(temp)) {
            String alertMsg = String.format(
                    "⚠️ Extremväder i %s: %.1f°C (%s)",
                    response.getName(),
                    temp,
                    response.getWeather().get(0).getDescription()
            );
            weatherAlertService.sendAlert(alertMsg);
            LOG.warn("Notis skickad: {}", alertMsg);
        }

        return saved;
    }

    public void sendTestAlert() {
        String message = "🧪 Testnotis från vädertjänsten – SNS funkar!";
        weatherAlertService.sendAlert(message);
    }


    public Iterable<WeatherEntry> getHistory() {
        return repository.findAllOrderByTimestampDesc();
    }

    private boolean isExtremeWeather(double temp) {
        return temp >= 30 || temp <= -15;
    }
}
