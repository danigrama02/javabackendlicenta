package com.licenta.licenta.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    String weatherId;
    String timestamp;
    String temperature;
    String precipitaion;
    String pressure;
    String weatherImg;
}
