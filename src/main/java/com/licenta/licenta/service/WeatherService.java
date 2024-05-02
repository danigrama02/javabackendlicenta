package com.licenta.licenta.service;

import com.google.gson.Gson;
import com.licenta.licenta.domain.*;
import com.licenta.licenta.domain.requests.WeatherRequest;
import com.licenta.licenta.repository.HistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WeatherService {

    private RestTemplate restTemplate;
    private String AI_URL= "http://127.0.0.1:8000/weather";

    private final HistoryRepository historyRepository;
    @Autowired
    public WeatherService(HistoryRepository historyRepository) {
        this.restTemplate = new RestTemplate();
        this.historyRepository = historyRepository;
    }

    public List<WeatherDto> getWeather(WeatherRequest weatherRequest) {
        Gson gson = new Gson();

        Integer currentHour = LocalDateTime.now().getHour();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(weatherRequest.getLocations(), headers);

        List<WeatherDto> weatherDtos = new ArrayList<>();
        ResponseEntity<String> response = restTemplate.postForEntity(AI_URL + "/predict", entity, String.class);
        if (response.getStatusCode().is2xxSuccessful())
        {
            System.out.println(response.getBody());
            Map<String, List<List<List<Double>>>> responseJson = gson.fromJson(response.getBody(), Map.class);
            List<Weather> weathers = new ArrayList<>();

            for (List<List<Double>> predictionPerDayForCoords : responseJson.get("predictions")) {
                int hoursIndex = 0;
                for (List<Double> prediction : predictionPerDayForCoords) {
                    Weather w = new Weather(prediction.get(0).toString(), prediction.get(2).toString(), prediction.get(3).toString(), prediction.get(1).toString());
                    if (currentHour == hoursIndex) {
                        weathers.add(w);
                        currentHour ++;
                        if (currentHour == 24) {
                            currentHour = 0;
                        }
                        break;
                    }
                    hoursIndex++;
                }

            }

            int i =0;
            for (Weather w : weathers) {
                WeatherDto weatherDto = new WeatherDto(i,w.getTemperature(), w.getPrecipitaion(),calculateWeatherImage(w));
                weatherDtos.add(weatherDto);
                i++;
            }
        }
        this.saveHistory(weatherRequest, weatherDtos);
        return weatherDtos;
    }

    private String calculateWeatherImage(Weather weather){

        String weatherImage = "";

        double temperature = Double.parseDouble(weather.getTemperature());
        double precipitation = Double.parseDouble(weather.getPrecipitaion());
        double cloudCoverage = Double.parseDouble(weather.getCloudCoverage());

        if (precipitation - 0.3 > 0.0){
            if (temperature < 3.0){
                weatherImage = "snow";
            }
            else {
                weatherImage = "rain";
            }
        }
        else{
            weatherImage = "sun";
        }
        if (cloudCoverage > 60.0 && !weatherImage.equals("rain") && !weatherImage.equals("snow")){
            weatherImage = "cloudy";
        }

        return weatherImage;
    }

    public List<History> getHistory(String username){
        username = username.replace("\"", "");
        username = username.strip();
        List<History> history = this.historyRepository.findByUsername(username);
        return history;
    }

    public void saveHistory(WeatherRequest weatherRequest, List<WeatherDto> weatherDtos){
        log.info("Saving history");
        double avgTemp = 0.0;
        double avgPrecipitation = 0.0;
        for (WeatherDto w : weatherDtos){
            avgTemp += Double.parseDouble(w.getTemperature());
            avgPrecipitation += Double.parseDouble(w.getPrecipitaion());
        }
        avgTemp = avgTemp / weatherDtos.size();
        avgPrecipitation = avgPrecipitation / weatherDtos.size();
        History history = new History();
        history.setUsername(weatherRequest.getUsername());
        history.setDate(LocalDateTime.now().toLocalDate().toString());
        history.setStartLocation(weatherRequest.getFromLocation());
        history.setEndLocation(weatherRequest.getToLocation());
        history.setAvgTemp(String.valueOf(avgTemp));
        history.setAvgPrecipitation(String.valueOf(avgPrecipitation));
        this.historyRepository.save(history);
    }



}
