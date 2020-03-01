package com.example.rectifierBackend.model;

import javax.persistence.*;

@Entity
@Table(name = "sample")
public class Sample {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Process process;

    public Sample(Process process) {
        this.process = process;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

}
