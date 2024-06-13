package com.code.camping.service;

import com.code.camping.entity.Weather;

import java.util.List;

public interface WeatherService {
    List<Weather> getWeatherData() throws Exception;
}
