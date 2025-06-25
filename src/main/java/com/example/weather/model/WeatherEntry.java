package com.example.weather.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_entries")
public class WeatherEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private double temperature;
    private int humidity;
    private String description;
    private double windSpeed;

    /**
     * Kolumnnamnet "timestamp" kan krocka med reservord i vissa databaser.
     * Därför mappar vi fältet till kolumnen entry_time istället.
     */
    @Column(name = "entry_time")
    private LocalDateTime timestamp;

    /* --- Konstruktörer --- */
    public WeatherEntry() { }

    public WeatherEntry(String city,
                        double temperature,
                        int humidity,
                        String description,
                        double windSpeed) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
        this.windSpeed = windSpeed;
        this.timestamp = LocalDateTime.now();
    }

    /* --- Getters & Setters --- */
    public Long getId() { return id; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
