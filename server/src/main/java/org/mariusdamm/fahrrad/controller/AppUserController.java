package org.mariusdamm.fahrrad.controller;

import org.mariusdamm.fahrrad.dto.AppUserDto;
import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/self")
    public ResponseEntity<AppUserDto> getSelf() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        AppUser user = appUserService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user.toDto());
    }
}
