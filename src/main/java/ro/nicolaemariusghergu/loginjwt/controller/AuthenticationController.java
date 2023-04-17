package ro.nicolaemariusghergu.loginjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.nicolaemariusghergu.loginjwt.model.dto.AuthenticationRequest;
import ro.nicolaemariusghergu.loginjwt.model.dto.AuthenticationResponse;
import ro.nicolaemariusghergu.loginjwt.model.dto.RegisterRequest;
import ro.nicolaemariusghergu.loginjwt.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(authService.registerRequest(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authRequest
    ) {
        return ResponseEntity.ok(authService.authenticationRequest(authRequest));
    }
}
