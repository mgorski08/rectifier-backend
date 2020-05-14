package com.example.rectifierBackend.model;

import javax.persistence.*;

@Entity
@Table(name = "bath")
public class Bath {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Process process;

    private double minVoltage;
    private double maxVoltage;
    private double minCurrent;
    private double maxCurrent;
    private double minTemperature;
    private double maxTemperature;
    private double minTime;
    private double maxTime;

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

    public double getMinVoltage() {
        return minVoltage;
    }

    public void setMinVoltage(double minVoltage) {
        this.minVoltage = minVoltage;
    }

    public double getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(double maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public double getMinCurrent() {
        return minCurrent;
    }

    public void setMinCurrent(double minCurrent) {
        this.minCurrent = minCurrent;
    }

    public double getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(double maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTime() {
        return minTime;
    }

    public void setMinTime(double minTime) {
        this.minTime = minTime;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
}
