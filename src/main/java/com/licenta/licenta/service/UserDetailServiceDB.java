package com.licenta.licenta.service;

import com.licenta.licenta.domain.User;
import com.licenta.licenta.domain.UserPrincipal;
import com.licenta.licenta.domain.requests.CreateAccountRequest;
import com.licenta.licenta.repository.UsersRepository;
import com.licenta.licenta.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserDetailServiceDB implements UserDetailsService {

    private final UsersRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final JwtTokenUtils tokenUtils;

    @Autowired
    public UserDetailServiceDB(UsersRepository userRepository, BCryptPasswordEncoder encoder, JwtTokenUtils tokenUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.info("No such user" + username);
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user.get());
    }

    public UserDetails loadUserByUsernameAndPassword(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.info("No such user" + username);
            throw new UsernameNotFoundException(username);
        }
        if (!encoder.matches(password, user.get().getPassword())) {
            log.info("Invalid password for user " + username);
            throw new UsernameNotFoundException(username);
        }
        log.info("Successful login for user " + username);
        return new UserPrincipal(user.get());
    }

    public void createAccount(CreateAccountRequest createAccountRequest) throws Exception{
        Optional<User> user = userRepository.findByUsername(createAccountRequest.getUsername());
        if (user.isPresent()){
            log.info("Username already in use " + createAccountRequest.getUsername());
            throw new Exception("Username already used");
        }
        User newUser = new User();
        newUser.setUsername(createAccountRequest.getUsername());
        newUser.setPassword(encoder.encode(createAccountRequest.getPassword()));
        userRepository.save(newUser);
    }

    public void logout() {
        //todo
    }
}
