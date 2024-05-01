package com.licenta.licenta.domain.requests;

import com.licenta.licenta.domain.Weather;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse implements Serializable {
    private Weather[] predictions;
}
