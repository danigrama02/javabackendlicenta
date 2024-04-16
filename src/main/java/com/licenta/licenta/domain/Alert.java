package com.licenta.licenta.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alert {
    String alertId;
    Double lat;
    Double len;
    String timestamp;
    String alertMessage;
    String alertImg;
}
