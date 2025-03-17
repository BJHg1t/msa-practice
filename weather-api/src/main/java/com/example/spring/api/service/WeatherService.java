package com.example.spring.api.service;

import com.example.spring.api.client.WeatherClient;
import com.example.spring.api.dto.WeatherRequestDTO;
import com.example.spring.api.dto.WeatherResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherResponseDTO getWeather(WeatherRequestDTO weatherRequestDTO) {
        return weatherClient.getUltraSrtFcst(
            weatherRequestDTO.getServiceKey(),
            weatherRequestDTO.getPageNo(),
            weatherRequestDTO.getNumOfRows(),
            weatherRequestDTO.getDataType(),
            weatherRequestDTO.getBase_date(),
            weatherRequestDTO.getBase_time(),
            weatherRequestDTO.getNx(),
            weatherRequestDTO.getNy()
        );
    }
}
