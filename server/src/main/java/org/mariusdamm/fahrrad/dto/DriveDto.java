package org.mariusdamm.fahrrad.dto;

import java.time.LocalDateTime;

public class DriveDto {
    private long id;
    private LocalDateTime date;

    public DriveDto() {
    }

    public DriveDto(long id, LocalDateTime date) {
        this.id = id;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
