package com.example.rectifierBackend.model;

import javax.persistence.*;

@Entity
@Table(name = "element")
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;



}
