package com.code.camping.service.impl;

import com.code.camping.controller.ErrorController;
import com.code.camping.entity.Weather;
import com.code.camping.service.WeatherService;
import com.code.camping.utils.DateTimeFormatUtil;
import com.code.camping.utils.dto.webResponse.PageResponse;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private static final String URL_STRING = "https://cuaca-gempa-rest-api.vercel.app/weather/jawa-barat/bandung";

    @Override
    public List<Weather> getWeatherData() throws Exception {
        // Membuat objek URL dengan URL yang diberikan
        URL url = new URL(URL_STRING);
        // Membuka koneksi HTTP ke URL.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Menyetel metode permintaan menjadi GET
        conn.setRequestMethod("GET");

        // Membaca respons dari koneksi
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        // Mendeklarasikan variabel untuk membaca setiap baris input
        String inputLine;
        // Membuat objek StringBuilder untuk menyimpan konten respons
        StringBuilder content = new StringBuilder();
        // Membaca setiap baris input
        while ((inputLine = in.readLine()) != null) {
            // Menambahkan baris input ke StringBuilder
            content.append(inputLine);
        }
        // Menutup BufferedReader
        in.close();
        // Menutup koneksi HTTP
        conn.disconnect();

        // Mengonversi respons menjadi objek JSON
        JSONObject jsonObject = new JSONObject(content.toString());
        // Mendapatkan objek data dari JSON
        JSONObject dataObject = jsonObject.getJSONObject("data");
        // Mendapatkan array parameter dari data
        JSONArray paramsArray = dataObject.getJSONArray("params");

        // Membuat daftar untuk menyimpan objek WeatherData
        List<Weather> weatherDataList = new ArrayList<>();

        for (int i = 0; i < paramsArray.length(); i++) {
            JSONObject paramObject = paramsArray.getJSONObject(i);
            if (paramObject.getString("id").equals("weather")) {
                JSONArray timesArray = paramObject.getJSONArray("times");

                for (int j = 0; j < timesArray.length(); j++) {
                    JSONObject timeObject = timesArray.getJSONObject(j);
                    String datetime = timeObject.getString("datetime");
                    String formattedDate = DateTimeFormatUtil.formatDate(datetime);
                    String formattedTime = DateTimeFormatUtil.formatTime(datetime);
                    String name = timeObject.getString("name");
                    weatherDataList.add(new Weather(formattedDate, formattedTime, name));
                }
            }
        }
        return weatherDataList;
    }

    // Add Page, Search tanggal dan waktu juga statusnya/name nya kalo di entity nya
    public PageResponse<Weather> getAllWithPagingAndFiltering(String date, String name, String time, int page, int size) throws Exception {
        List<Weather> allWeatherData = getWeatherData();
        List<Weather> filteredData = allWeatherData.stream()
                .filter(data -> (date == null || data.getDate().equals(date)) &&
                        (name == null || data.getName().equalsIgnoreCase(name)) &&
                        (time == null || data.getTime().equals(time)))
                .collect(Collectors.toList());

        if (filteredData.isEmpty()) {
            throw new ErrorController.WeatherDataNotFoundException("Weather Data Not Found");
        }

        int start = page * size;
        int end = Math.min(start + size, filteredData.size());
        if (start >= filteredData.size()) {
            return new PageResponse<>(List.of(), 0L, 0, page, size);
        }
        List<Weather> pageContent = filteredData.subList(start, end);
        return new PageResponse<>(pageContent, (long) filteredData.size(), (filteredData.size() + size - 1) / size, page, size);
    }

    

}
