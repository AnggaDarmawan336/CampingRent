package com.code.camping.controller;


import com.code.camping.entity.Weather;
import com.code.camping.service.WeatherService;
import com.code.camping.utils.dto.webResponse.PageResponse;
import com.code.camping.utils.dto.webResponse.Res;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class WeatherController {

    // Mengotomatisasi penyuntikan dependensi untuk WeatherService
    private final WeatherService weatherService;

    // Menetapkan endpoint GET untuk /weather
    @GetMapping("/weather")
    public ResponseEntity<?> getWeather() {
        try {
            return Res.renderJson(weatherService.getWeatherData(), "Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return Res.renderJson(null, "Failed to retrieve weather data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Penambahan Request Param untuk menampilkan semua Data jga bisa, juga mau cari
    // nama atau status cuaca nya juga tanggalnya bisa juga di filter
    @GetMapping("/weather/search")
    public ResponseEntity<?> searchWeatherData(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String time,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        PageResponse<Weather> pageResponse = weatherService.getAllWithPagingAndFiltering(date, name, time, page, size);
        return Res.renderJson(pageResponse, "The weather data was successfully found", HttpStatus.OK);
    }
}
