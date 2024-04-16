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

        Weather w1 = new Weather("1","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w2 = new Weather("2","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w3 = new Weather("3","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w4 = new Weather("4","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w5 = new Weather("5","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w6 = new Weather("6","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w7 = new Weather("7","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w8 = new Weather("8","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w9 = new Weather("9","123456","10.34°C","10.23mm","10.23atm","123321424");
        Weather w10 = new Weather("10","123456","10.34°C","10.23mm","10.23atm","123321424");
        List<Weather> weathers = List.of(w1,w2,w3,w4,w5,w6,w7,w8,w9,w10);

        return weathers;
    }

    public List<Alert> getAlerts(List<Point> points){
        //TODO same as weather but with alerts
        Alert a1 = new Alert("1",10.234,10.234,"123456","123321424","123321424");
        Alert a2 = new Alert("2",10.234,10.234,"123456","123321424","123321424");
        Alert a3 = new Alert("3",10.234,10.234,"123456","123321424","123321424");
        Alert a4 = new Alert("4",10.234,10.234,"123456","123321424","123321424");
        Alert a5 = new Alert("5",10.234,10.234,"123456","123321424","123321424");
        Alert a6 = new Alert("6",10.234,10.234,"123456","123321424","123321424");
        Alert a7 = new Alert("7",10.234,10.234,"123456","123321424","123321424");
        Alert a8 = new Alert("8",10.234,10.234,"123456","123321424","123321424");

        List<Alert> alerts = List.of(a1,a2,a3,a4,a5,a6,a7,a8);
        return alerts;
    }


}
