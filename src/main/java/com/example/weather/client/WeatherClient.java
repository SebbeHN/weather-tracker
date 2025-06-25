package com.example.weather.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import com.example.weather.model.WeatherResponse;

@Client("${weather.api.base-url}")
public interface WeatherClient {

    @Get("/weather")
    WeatherResponse getWeatherByCity(
        @QueryValue("q") String city,
        @QueryValue("appid") String apiKey,
        @QueryValue("units") String units);
}
