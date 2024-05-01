package com.licenta.licenta.controller;

import com.google.gson.Gson;
import com.licenta.licenta.domain.Location;
import com.licenta.licenta.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public final ResponseEntity<?> getWeather(@RequestBody  String locations){
        log.info("Sending response for requested weather");
        log.info("Locations: " + locations);
        return ResponseEntity.ok(weatherService.getWeather(locations));
    }


    @PostMapping("/history")
    public final ResponseEntity<?> getHistory(){
        log.info("Sending history response");
        return ResponseEntity.ok("da");
    }
}
