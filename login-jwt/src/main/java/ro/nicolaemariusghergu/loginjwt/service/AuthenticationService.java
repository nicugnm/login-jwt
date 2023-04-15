package ro.nicolaemariusghergu.loginjwt.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.nicolaemariusghergu.loginjwt.model.User;
import ro.nicolaemariusghergu.loginjwt.model.dto.AuthenticationRequest;
import ro.nicolaemariusghergu.loginjwt.model.dto.AuthenticationResponse;
import ro.nicolaemariusghergu.loginjwt.model.dto.RegisterRequest;
import ro.nicolaemariusghergu.loginjwt.model.enums.Role;
import ro.nicolaemariusghergu.loginjwt.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationResponse registerRequest(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticationRequest(AuthenticationRequest authRequest) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        var user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
