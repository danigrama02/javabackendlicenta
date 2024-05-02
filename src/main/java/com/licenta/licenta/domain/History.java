package com.licenta.licenta.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    private String _id;
    private String username;
    private String date;
    private String startLocation;
    private String endLocation;
    private String avgTemp;
    private String avgPrecipitation;
}
