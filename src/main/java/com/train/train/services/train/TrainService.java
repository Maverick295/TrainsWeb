package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.models.TrainModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TrainService {
    List<Train> createNewTrains(Integer maxTrains, LocalDate startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow);
    List<TrainModel> convertToTrainModel(List<Train> trainsList);
    List<Integer> distributePeopleRandomly(Integer peopleCount, int interval);
    List<Integer> findTrainNumbers(List<Integer> fromMoscowToPiter, List<Integer> fromPiterToMoscow);
    String generateUuid();
    String generateSerialNumber();
}
