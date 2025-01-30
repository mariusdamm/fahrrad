package org.mariusdamm.fahrrad.controller;

import org.mariusdamm.fahrrad.dto.DriveDto;
import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.entity.Drive;
import org.mariusdamm.fahrrad.exception.ConstraintException;
import org.mariusdamm.fahrrad.service.AppUserService;
import org.mariusdamm.fahrrad.service.DriveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("")
    public ResponseEntity<Collection<DriveDto>> getYearlyDrivesOfUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = appUserService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Collection<Drive> drives = driveService.getYearlyDrivesOfUser(user);
        Collection<DriveDto> dtos = new ArrayList<>();
        for (Drive drive : drives){
            dtos.add(drive.toDto());
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("")
    public ResponseEntity<DriveDto> createDrive(@RequestBody DriveDto drive) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = appUserService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            DriveDto dto = driveService.createDrive(drive, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (ConstraintException e) {
            return ResponseEntity.status(HttpStatus.FOUND).body(null);
        }

    }
}