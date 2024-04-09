package com.licenta.licenta.controller;

import com.licenta.licenta.domain.UserPrincipal;
import com.licenta.licenta.domain.requests.*;
import com.licenta.licenta.service.UserDetailServiceDB;
import com.licenta.licenta.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/authentication")
public class JwtAuthenticationController {

    private final JwtTokenUtils jwtTokenUtils;

    private final UserDetailServiceDB userDetailServiceDB;

    @Autowired
    public JwtAuthenticationController(JwtTokenUtils jwtTokenUtils, UserDetailServiceDB userDetailServiceDB) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailServiceDB = userDetailServiceDB;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody final JwtTokenRequest tokenRequest) {
        log.info("New login request for user " + tokenRequest.getUsername());

        return ResponseEntity.ok(new JwtTokenResponse(
                jwtTokenUtils.generateToken(
                        (UserPrincipal) this.userDetailServiceDB.loadUserByUsernameAndPassword(
                                tokenRequest.getUsername()
                                , tokenRequest.getPassword()))));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestBody final LogoutRequest logoutRequest){
        log.info("Logging out user " + jwtTokenUtils.getUsernameFromToken(logoutRequest.getToken()));
        this.userDetailServiceDB.logout(logoutRequest.getToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody final CreateAccountRequest createAccountRequest){
        log.info("Request for creating account with username " + createAccountRequest.getUsername());
        try{
            userDetailServiceDB.createAccount(createAccountRequest);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
}
