package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.forms.UpdateTrainInfoForm;
import com.train.train.models.TrainModel;
import com.train.train.repositories.TrainRepository;
import com.train.train.services.model.ModelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrainServiceImpl implements TrainService {
    private final TrainRepository trainRepository;
    private final ModelService modelService;
    private static final String DEFAULT_MILEAGE = "0";
    private static final String DEFAULT_CYCLE = "IS0";
    private static final String DEFAULT_WORK_DAYS = "0";

    @Autowired
    public TrainServiceImpl(
            TrainRepository trainRepository,
            ModelService modelService
    ) {
        this.trainRepository = trainRepository;
        this.modelService = modelService;
    }

    @Override
    public Train findTrainById(Long id) {
        return trainRepository.findTrainById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Train findTrainByUuid(String uuid) {
        return trainRepository.findTrainByUuid(uuid)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void saveTrain(Train train) {
        trainRepository.save(train);
    }

    @Override
    public Train createNewTrain() {
        return new Train(
                UUID.randomUUID().toString(),
                DEFAULT_MILEAGE,
                DEFAULT_WORK_DAYS,
                DEFAULT_CYCLE
        );
    }

    @Override
    public Train editTrain(Train train, UpdateTrainInfoForm form) {
        return train
                .setMileage(form.getMileage())
                .setDaysInWork(form.getDaysInWork())
                .setCycle(form.getCycle());
    }

    @Override
    public Page<TrainModel> findAllTrains(Pageable pageable) {
        return trainRepository.findAll(pageable)
                .map(modelService::getTrainModel);
    }

    @Override
    public void deleteTrainByUuid(String uuid) {
        trainRepository.deleteByUuid(uuid);
    }
}
