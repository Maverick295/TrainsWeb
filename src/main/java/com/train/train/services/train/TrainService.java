package com.train.train.services.train;

import com.train.train.models.TrainModel;

import java.time.LocalDate;
import java.util.List;

public interface TrainService {
    List<TrainModel> createNewTrains(Integer maxTrains, LocalDate startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow);
    List<Integer> distributePeopleRandomly(Integer peopleCount, int interval);
    void clearListsForRandomInterval();
    String generateUuid();
    String generateSerialNumber();
}
