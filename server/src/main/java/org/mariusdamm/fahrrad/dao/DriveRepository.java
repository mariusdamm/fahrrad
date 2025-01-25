package org.mariusdamm.fahrrad.dao;

import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DriveRepository extends JpaRepository<Drive, Long> {
    Collection<Drive> findByOwner(AppUser owner);
}
