package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.models.TrainModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TrainService {
    Train getTrainById(Long id);
    Train getTrainBySerialNumber(String serialNumber);
    void saveTrain(Train train);
    void createNewTrains(Integer maxTrains, LocalDate startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow);
    Page<TrainModel> findAllTrains(Pageable pageable);
}
