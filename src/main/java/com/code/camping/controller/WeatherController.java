package com.code.camping.controller;


import com.code.camping.entity.Weather;
import com.code.camping.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public List<Weather> getWeather(){
        try {
            return weatherService.getWeatherData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
