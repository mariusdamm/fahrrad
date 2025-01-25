package org.mariusdamm.fahrrad.service;

import org.mariusdamm.fahrrad.dao.DriveRepository;
import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.entity.Drive;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DriveService {
    
    private final DriveRepository driveRepository;

    public DriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public Collection<Drive> getYearlyDrivesOfUser(AppUser user) {
        return driveRepository.findByOwner(user);
    }
}
