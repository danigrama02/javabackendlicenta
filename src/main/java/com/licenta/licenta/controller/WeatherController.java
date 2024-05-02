package com.licenta.licenta.controller;

import com.licenta.licenta.domain.requests.WeatherRequest;
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
    public final ResponseEntity<?> getWeather(@RequestBody WeatherRequest weatherRequest){
        log.info("Sending response for requested weather");
        log.info("Locations: " + weatherRequest.getLocations());
        return ResponseEntity.ok(weatherService.getWeather(weatherRequest));
    }


    @PostMapping("/history")
    public final ResponseEntity<?> getHistory(@RequestBody String username){
        log.info("Sending history response");
        log.info("Username: " + username);
        return ResponseEntity.ok(this.weatherService.getHistory(username));
    }
}
