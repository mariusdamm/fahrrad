package org.mariusdamm.fahrrad.controller;

import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.entity.Drive;
import org.mariusdamm.fahrrad.service.AppUserService;
import org.mariusdamm.fahrrad.service.DriveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/drives")
public class DriveController {
    
    private final DriveService driveService;
    private final AppUserService appUserService;

    public DriveController(DriveService driveService, AppUserService appUserService) {
        this.driveService = driveService;
        this.appUserService = appUserService;
    }
    
    @GetMapping
    public ResponseEntity<Collection<Drive>> getYearlyDrivesOfUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = appUserService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        Collection<Drive> drives = driveService.getYearlyDrivesOfUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(drives);
    }
}
