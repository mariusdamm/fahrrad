package org.mariusdamm.fahrrad.dao;

import org.mariusdamm.fahrrad.entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveRepository extends JpaRepository<Drive, Long> {
}
