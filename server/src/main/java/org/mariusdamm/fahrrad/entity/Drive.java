package org.mariusdamm.fahrrad.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "drive")
public class Drive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drive_id", nullable = false)
    long id;
    @Column(name = "drive_date")
    LocalDateTime date;
    @ManyToOne()
    @JoinColumn(name = "drive_owner")
    private AppUser owner;

    public Drive() {
    }

    public Drive(long id, LocalDateTime date, AppUser owner) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }
}
