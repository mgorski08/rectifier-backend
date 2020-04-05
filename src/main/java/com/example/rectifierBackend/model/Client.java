package com.example.rectifierBackend.model;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String companyName;
    private String nip;
    private String address;
    private String city;
    private String zipCode;
}
