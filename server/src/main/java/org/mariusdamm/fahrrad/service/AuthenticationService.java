package org.mariusdamm.fahrrad.service;

import org.mariusdamm.fahrrad.dao.AppUserRepository;
import org.mariusdamm.fahrrad.dao.RoleRepository;
import org.mariusdamm.fahrrad.dto.LoginDto;
import org.mariusdamm.fahrrad.dto.LoginResponseDto;
import org.mariusdamm.fahrrad.dto.RegisterDto;
import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.entity.Role;
import org.mariusdamm.fahrrad.exception.ConstraintException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService, AppUserRepository appUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto loginUser(LoginDto body) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword()));

        String token = tokenService.generateJwt(auth);
        return new LoginResponseDto(token);
    }

    public void registerUser(RegisterDto body) throws ConstraintException {
        if (body.getUsername().isBlank() || body.getPassword().isBlank()) {
            throw new IllegalArgumentException("Username, password and name must not be empty");
        }

        if (appUserRepository.findByUsername(body.getUsername()).isPresent()) {
            throw new ConstraintException("Username " + body.getUsername() + " is already taken");
        }

        Role userRole = roleRepository.findByAuthority("USER").orElseGet(() -> roleRepository.save(new Role(0, "USER", new ArrayList<>())));

        List<Role> roles = List.of(userRole);
        appUserRepository.save(new AppUser(
                0, body.getUsername(), passwordEncoder.encode(body.getPassword()), roles, new ArrayList<>()
        ));
    }
}
