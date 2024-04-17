package com.train.train.forms;

public class UpdateTrainInfoForm {
    private String mileage;
    private String daysInWork;
    private String cycle;

    public String getMileage() {
        return mileage;
    }

    public UpdateTrainInfoForm setMileage(String mileage) {
        this.mileage = mileage;
        return this;
    }

    public String getDaysInWork() {
        return daysInWork;
    }

    public UpdateTrainInfoForm setDaysInWork(String daysInWork) {
        this.daysInWork = daysInWork;
        return this;
    }

    public String getCycle() {
        return cycle;
    }

    public UpdateTrainInfoForm setCycle(String cycle) {
        this.cycle = cycle;
        return this;
    }
}
