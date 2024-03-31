package com.licenta.licenta.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest implements Serializable {
    private static final long serialVersionUID = 1212121212L;

    private String token;
}
