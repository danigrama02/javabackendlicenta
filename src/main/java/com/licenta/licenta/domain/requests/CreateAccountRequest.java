package com.licenta.licenta.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest implements Serializable {
    private static final long serialVersionUID = 2222222L;

    private String username;
    private String password;
}
