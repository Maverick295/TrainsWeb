package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.models.TrainModel;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainService {
    List<TrainModel> exec(Integer maxTrains, LocalDateTime startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow);
    void createNewTrains(Integer maxTrains, LocalDateTime startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow);
    List<TrainModel> convertToTrainModel(List<Train> trainsList);
    List<Integer> distributePeopleRandomly(Integer peopleCount, int interval);
    List<Integer> findTrainNumbers(List<Integer> fromMoscowToPiter, List<Integer> fromPiterToMoscow);
    List<Double> countIntervals(List<Integer> trainNum);
    void setTrainStatus();
}
