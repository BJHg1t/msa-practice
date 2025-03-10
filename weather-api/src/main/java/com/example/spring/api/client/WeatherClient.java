package com.example.spring.api.client;

import com.example.spring.api.dto.WeatherResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient (name = "weatherClient", url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst")
public interface WeatherClient {

    @GetMapping(produces = "application/json")
    WeatherResponseDTO getUltraSrtFcst(
            @RequestParam String serviceKey,
            @RequestParam int pageNo,
            @RequestParam int numOfRows,
            @RequestParam String  dataType,
            @RequestParam String  base_date,
            @RequestParam String  base_time,
            @RequestParam int nx,
            @RequestParam int ny
    );
}