package org.mariusdamm.fahrrad.controller;

import org.mariusdamm.fahrrad.dto.LoginDto;
import org.mariusdamm.fahrrad.dto.LoginResponseDto;
import org.mariusdamm.fahrrad.dto.RegisterDto;
import org.mariusdamm.fahrrad.exception.ConstraintException;
import org.mariusdamm.fahrrad.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginDto body) {
        body.setUsername(body.getUsername().replaceAll("\\s", ""));
        body.setPassword(body.getPassword().replaceAll("\\s", ""));
        try {
            LoginResponseDto dto = authenticationService.loginUser(body);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> registerUser(@RequestBody RegisterDto body) {
        body.setUsername(body.getUsername().replaceAll("\\s", ""));
        body.setPassword(body.getPassword().replaceAll("\\s", ""));
        try {
            authenticationService.registerUser(body);
            LoginDto loginDto = new LoginDto(body.getUsername(), body.getPassword());
            LoginResponseDto dto = authenticationService.loginUser(loginDto);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ConstraintException e) {
            return ResponseEntity.status(HttpStatus.FOUND).body(null);
        }
    }
}
