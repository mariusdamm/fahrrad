package org.mariusdamm.fahrrad.entity;

import jakarta.persistence.*;
import org.mariusdamm.fahrrad.dto.DriveDto;

@Entity
@Table(name = "drive")
public class Drive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drive_id", nullable = false)
    long id;
    @Column(name = "drive_date", nullable = false)
    String date;
    @ManyToOne()
    @JoinColumn(name = "drive_owner")
    private AppUser owner;

    public Drive() {
    }

    public Drive(long id, String date, AppUser owner) {
        this.id = id;
        this.date = date;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public DriveDto toDto(){
        return new DriveDto(id, date);
    }
}
