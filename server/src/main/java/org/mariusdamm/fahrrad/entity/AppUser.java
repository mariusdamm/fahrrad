package org.mariusdamm.fahrrad.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "appuser")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long id;
    @Column(name = "user_username", unique = true, nullable = false)
    private String username;
    @Column(name = "user_password", nullable = false)
    private String password;
    @Column(name = "appuser_name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> authorities = new ArrayList<>();
    @OneToMany(mappedBy = "owner")
    private transient Collection<Drive> drives = new ArrayList<>();

    public AppUser() {
    }

    public AppUser(long id, String username, String password, String name, Collection<Role> authorities, Collection<Drive> drives) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
        this.drives = drives;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<Drive> getDrives() {
        return drives;
    }

    public void setDrives(Collection<Drive> drives) {
        this.drives = drives;
    }
}
