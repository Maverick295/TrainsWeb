package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.forms.UpdateTrainInfoForm;
import com.train.train.models.TrainModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainService {
    Train findTrainById(Long id);
    Train findTrainByUuid(String uuid);
    void saveTrain(Train train);
    Train createNewTrain();
    Train editTrain(Train train, UpdateTrainInfoForm form);
    Page<TrainModel> findAllTrains(Pageable pageable);
    void deleteTrainByUuid(String uuid);
}
