package com.train.train.models;

public class TrainModel {
    private String uuid;
    private String mileage;
    private String daysInWork;
    private String cycle;

    public String getUuid() {
        return uuid;
    }

    public TrainModel setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getMileage() {
        return mileage;
    }

    public TrainModel setMileage(String mileage) {
        this.mileage = mileage;
        return this;
    }

    public String getDaysInWork() {
        return daysInWork;
    }

    public TrainModel setDaysInWork(String daysInWork) {
        this.daysInWork = daysInWork;
        return this;
    }

    public String getCycle() {
        return cycle;
    }

    public TrainModel setCycle(String cycle) {
        this.cycle = cycle;
        return this;
    }
}
