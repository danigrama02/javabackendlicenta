package com.licenta.licenta.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
    Integer orderNr;
    String temperature;
    String precipitaion;
    String weatherImage;
}
