package com.code.camping.service;

import com.code.camping.entity.Weather;
import com.code.camping.utils.dto.webResponse.PageResponse;

import java.util.List;

public interface WeatherService {
    List<Weather> getWeatherData() throws Exception;

    PageResponse<Weather> getAllWithPagingAndFiltering(String date, String status, String time, int page, int size) throws Exception;
   
}
