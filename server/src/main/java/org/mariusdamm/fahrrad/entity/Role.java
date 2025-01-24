package org.mariusdamm.fahrrad.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;
    @Column(name = "role_authority", nullable = false, unique = true)
    private String authority;
    @ManyToMany(mappedBy = "authorities")
    private Collection<AppUser> users = new ArrayList<>();

    public Role() {
    }

    public Role(long id, String authority, Collection<AppUser> users) {
        this.id = id;
        this.authority = authority;
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<AppUser> users) {
        this.users = users;
    }
}
