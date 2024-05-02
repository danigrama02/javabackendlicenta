package com.licenta.licenta.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRequest implements Serializable {
    private static final long serialVersionUID = 1111111L;

    private String locations;
    private String fromLocation;
    private String toLocation;
    private String username;
}
