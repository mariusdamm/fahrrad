package org.mariusdamm.fahrrad.service;

import org.mariusdamm.fahrrad.dao.DriveRepository;
import org.mariusdamm.fahrrad.dto.DriveDto;
import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.entity.Drive;
import org.mariusdamm.fahrrad.exception.ConstraintException;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Collection;
import java.util.Objects;

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

    public DriveDto createDrive(DriveDto drive, AppUser user) throws ConstraintException {
        Collection<Drive> drives = driveRepository.findByOwner(user);
        for (Drive d: drives){
            if(Objects.equals(d.getDate(), drive.getDate()))
                throw new ConstraintException("Drive for this date already registered");
        }

        Drive newDrive = new Drive(0, drive.getDate(), user);
        newDrive = driveRepository.save(newDrive);
        return newDrive.toDto();
    }
}
