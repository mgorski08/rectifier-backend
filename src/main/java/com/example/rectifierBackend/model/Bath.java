package com.example.rectifierBackend.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "bath")
public class Bath {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
