package org.mariusdamm.fahrrad.dto;

public class AppUserDto {

    private long id;
    private String username;
    private String name;

    public AppUserDto() {
    }

    public AppUserDto(long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
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
}
