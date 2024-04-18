package com.train.train.forms;

import java.time.LocalDateTime;

public class TrainCreateForm {
    private Integer maxTrains;
    private Integer fromMoscowToPiter;
    private Integer fromPiterToMoscow;
    private LocalDateTime startDate;

    public Integer getMaxTrains() {
        return maxTrains;
    }

    public TrainCreateForm setMaxTrains(Integer maxTrains) {
        this.maxTrains = maxTrains;
        return this;
    }

    public Integer getFromMoscowToPiter() {
        return fromMoscowToPiter;
    }

    public TrainCreateForm setFromMoscowToPiter(Integer fromMoscowToPiter) {
        this.fromMoscowToPiter = fromMoscowToPiter;
        return this;
    }

    public Integer getFromPiterToMoscow() {
        return fromPiterToMoscow;
    }

    public TrainCreateForm setFromPiterToMoscow(Integer fromPiterToMoscow) {
        this.fromPiterToMoscow = fromPiterToMoscow;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public TrainCreateForm setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }
}
