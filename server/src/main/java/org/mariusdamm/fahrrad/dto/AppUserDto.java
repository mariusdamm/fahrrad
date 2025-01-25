package org.mariusdamm.fahrrad.dto;

import java.util.ArrayList;
import java.util.Collection;

public class AppUserDto {

    private long id;
    private String username;
    private String name;
    private Collection<DriveDto> drives = new ArrayList<>();

    public AppUserDto() {
    }

    public AppUserDto(long id, String username, String name, Collection<DriveDto> drives) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.drives = drives;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<DriveDto> getDrives() {
        return drives;
    }

    public void setDrives(Collection<DriveDto> drives) {
        this.drives = drives;
    }
}
