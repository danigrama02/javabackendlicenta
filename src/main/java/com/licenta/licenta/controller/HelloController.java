package com.licenta.licenta.controller;

import com.licenta.licenta.domain.User;
import com.licenta.licenta.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class HelloController {

    private UsersRepository userRepository;

    @Autowired
    public HelloController(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/hello")
    public final ResponseEntity<Optional<User>> getHello(){
        log.info("Sendiong response hello");
        List<User> user = userRepository.findAll();
        return ResponseEntity.ok(Optional.of(user.get(0)));
    }
}
