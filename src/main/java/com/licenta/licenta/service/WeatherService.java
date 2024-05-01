package com.licenta.licenta.service;

import com.google.gson.Gson;
import com.licenta.licenta.domain.Alert;
import com.licenta.licenta.domain.Location;
import com.licenta.licenta.domain.Weather;
import com.licenta.licenta.domain.WeatherDto;
import com.licenta.licenta.domain.requests.WeatherResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private RestTemplate restTemplate;
    private String AI_URL= "http://127.0.0.1:8000/weather";

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    public List<WeatherDto> getWeather(String locations) {
        Gson gson = new Gson();

        Integer currentHour = LocalDateTime.now().getHour();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(locations, headers);

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
        return weatherDtos;
    }

    private String calculateWeatherImage(Weather weather){

        String weatherImage = "";

        Double temperature = Double.valueOf(weather.getTemperature());
        Double precipitation = Double.valueOf(weather.getPrecipitaion());
        Double cloudCoverage = Double.valueOf(weather.getCloudCoverage());

        if (precipitation - 0.001 > 0.0){
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
        if (cloudCoverage > 60.0){
            weatherImage = weatherImage + "_cloudy";
        }
        else {
            weatherImage = weatherImage + "_clear";
        }

        return weatherImage;
    }


}
