package com.train.train.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String mileage;
    private String daysInWork;
    private String cycle;

    public Train() {

    }

    public Train(String mileage, String daysInWork, String cycle) {
        this.mileage = mileage;
        this.daysInWork = daysInWork;
        this.cycle = cycle;
    }

    public Train(String uuid, String mileage, String daysInWork, String cycle) {
        this.uuid = uuid;
        this.mileage = mileage;
        this.daysInWork = daysInWork;
        this.cycle = cycle;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public Train setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getMileage() {
        return mileage;
    }

    public Train setMileage(String mileage) {
        this.mileage = mileage;
        return this;
    }

    public String getDaysInWork() {
        return daysInWork;
    }

    public Train setDaysInWork(String daysInWork) {
        this.daysInWork = daysInWork;
        return this;
    }

    public String getCycle() {
        return cycle;
    }

    public Train setCycle(String cycle) {
        this.cycle = cycle;
        return this;
    }
}
