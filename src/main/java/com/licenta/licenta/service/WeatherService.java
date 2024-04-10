package com.licenta.licenta.service;

import com.licenta.licenta.domain.Alert;
import com.licenta.licenta.domain.Point;
import com.licenta.licenta.domain.Weather;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {
    public List<Weather> getWeather(List<Point> points) {
        //TODO add functionality to make a call to pithon service with the list of points and return the list of weather eventuali parse it to send it better to angular
        return null;
    }

    public List<Weather> getAlerts(List<Point> points){
        //TODO same as weather but with alerts
        return null;
    }


}
