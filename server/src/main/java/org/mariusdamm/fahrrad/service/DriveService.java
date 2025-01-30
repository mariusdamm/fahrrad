package org.mariusdamm.fahrrad.service;

import org.mariusdamm.fahrrad.dao.DriveRepository;
import org.mariusdamm.fahrrad.dto.DriveDto;
import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.entity.Drive;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Collection;

@Service
public class DriveService {
    
    private final DriveRepository driveRepository;

    public DriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public Collection<Drive> getYearlyDrivesOfUser(AppUser user) {
        Collection<Drive> drives = driveRepository.findByOwner(user);
        String currentYear = String.valueOf(Year.now().getValue());
        drives.removeIf(drive -> !drive.getDate().startsWith(currentYear));
        return drives;
    }

    public DriveDto createDrive(DriveDto drive, AppUser user) {
        Drive newDrive = new Drive(0, drive.getDate(), user);
        newDrive = driveRepository.save(newDrive);
        return newDrive.toDto();
    }
}
