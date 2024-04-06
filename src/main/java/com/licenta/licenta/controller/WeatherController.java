package com.licenta.licenta.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/weather")
public class WeatherController {

    @Autowired
    public WeatherController() {
    }

    @PostMapping("/forecast")
    public final ResponseEntity<?> getWeather(){
        log.info("Sending response for requested weather");

        return ResponseEntity.ok("da");
    }

    @PostMapping("/alerts")
    public final ResponseEntity<?> getAlerts(){
        log.info("Sending alerts");
        return ResponseEntity.ok("da");
    }

    @PostMapping("/history")
    public final ResponseEntity<?> getHistory(){
        log.info("Sending history response");
        return ResponseEntity.ok("da");
    }
}
