package com.example.spring.api.controller;

import com.example.spring.api.dto.WeatherRequestDTO;
import com.example.spring.api.dto.WeatherResponseDTO;
import com.example.spring.api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherApiController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherResponseDTO getData(
            @RequestParam String serviceKey,
            @RequestParam int pageNo,
            @RequestParam int numOfRows,
            @RequestParam String  dataType,
            @RequestParam String  base_date,
            @RequestParam String  base_time,
            @RequestParam int nx,
            @RequestParam int ny
    ) {
        WeatherRequestDTO weatherRequestDTO = WeatherRequestDTO.builder()
                .serviceKey(serviceKey)
                .pageNo(pageNo)
                .numOfRows(numOfRows)
                .dataType(dataType)
                .base_date(base_date)
                .base_time(base_time)
                .nx(nx)
                .ny(ny)
                .build();

        return weatherService.getWeather(weatherRequestDTO);
    }
}
