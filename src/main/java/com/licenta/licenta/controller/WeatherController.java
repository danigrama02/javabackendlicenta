package com.licenta.licenta.controller;

import com.licenta.licenta.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;
    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/forecast")
    public final ResponseEntity<?> getWeather(){
        log.info("Sending response for requested weather");

        return ResponseEntity.ok(weatherService.getWeather(null));
    }

    @PostMapping("/alerts")
    public final ResponseEntity<?> getAlerts(){
        log.info("Sending alerts");
        return ResponseEntity.ok(weatherService.getAlerts(null));
    }

    @PostMapping("/history")
    public final ResponseEntity<?> getHistory(){
        log.info("Sending history response");
        return ResponseEntity.ok("da");
    }
}
